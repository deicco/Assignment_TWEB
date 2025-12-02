package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crew")
public class CrewController {

    @Autowired
    private CrewService crewService;

    // GET /crew (Ottieni tutte le associazioni)
    @GetMapping
    public List<Crew> getAllCrew() {
        return crewService.getAllCrew();
    }

    // GET /crew/movie/{movieId} (Ottieni tutti i membri del crew per ID Film)
    @GetMapping("/movie/{movieId}")
    public List<Crew> getCrewByMovieId(@PathVariable Long movieId) {
        return crewService.getCrewByMovieId(movieId);
    }

    // GET /crew/{movieId}/{crewName} (Ottieni un membro specifico tramite chiave composta)
    @GetMapping("/{movieId}/{crewName}")
    public ResponseEntity<Crew> getCrewByCompositeId(@PathVariable Long movieId, @PathVariable String crewName) {
        Optional<Crew> crew = crewService.getCrewByCompositeId(movieId, crewName);
        return crew.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /crew (Crea un nuovo membro del crew con DTO)
    @PostMapping
    public Crew createCrew(@RequestBody CrewDTO crewDto) {
        return crewService.createCrew(crewDto);
    }

    // PUT /crew/{movieId}/{crewName} (Aggiorna il ruolo di un membro del crew)
    @PutMapping("/{movieId}/{crewName}")
    public ResponseEntity<Crew> updateCrew(@PathVariable Long movieId, @PathVariable String crewName, @RequestBody CrewDTO updatedCrewDto) {
        Crew result = crewService.updateCrew(movieId, crewName, updatedCrewDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /crew/{movieId}/{crewName} (Elimina un membro del crew per chiave composta)
    @DeleteMapping("/{movieId}/{crewName}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long movieId, @PathVariable String crewName) {
        boolean deleted = crewService.deleteCrew(movieId, crewName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}