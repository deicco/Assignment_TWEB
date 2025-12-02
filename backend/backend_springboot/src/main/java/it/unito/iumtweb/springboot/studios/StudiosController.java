package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studios")
public class StudiosController {

    @Autowired
    private StudiosService studiosService;

    // GET /studios (Ottieni tutte le associazioni)
    @GetMapping
    public List<Studios> getAllStudios() {
        return studiosService.getAllStudios();
    }

    // GET /studios/movie/{id} (Ottieni tutti gli studi per ID film) (Nuovo)
    @GetMapping("/movie/{movieId}")
    public List<Studios> getStudiosByMovieId(@PathVariable Long movieId) {
        return studiosService.getStudiosByMovieId(movieId);
    }

    // GET /studios/{movieId}/{studioName} (Ottieni associazione specifica per chiave composta) (Nuovo)
    @GetMapping("/{movieId}/{studioName}")
    public ResponseEntity<Studios> getStudioByCompositeKey(@PathVariable Long movieId, @PathVariable String studioName) {
        Optional<Studios> studio = studiosService.getStudioByCompositeKey(movieId, studioName);
        return studio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /studios (Ora usa DTO)
    @PostMapping
    public Studios createStudio(@RequestBody StudiosDTO studioDto) {
        return studiosService.createStudio(studioDto);
    }

    // PUT /studios/{movieId}/{studioName} (Aggiornamento con chiave composta e DTO)
    @PutMapping("/{movieId}/{studioName}")
    public ResponseEntity<Studios> updateStudio(
            @PathVariable Long movieId,
            @PathVariable String studioName,
            @RequestBody StudiosDTO updatedStudioDto
    ) {
        // L'update qui è più un check di esistenza/riscrittura, dato che non ci sono campi non-chiave
        Optional<Studios> result = studiosService.updateStudio(movieId, studioName, updatedStudioDto);

        return result
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /studios/{movieId}/{studioName} (Elimina con chiave composta)
    @DeleteMapping("/{movieId}/{studioName}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long movieId, @PathVariable String studioName) {
        boolean deleted = studiosService.deleteStudio(movieId, studioName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}