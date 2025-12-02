package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posters")
public class PosterController {

    @Autowired
    private PostersService postersService;

    // GET /posters (Invariato)
    @GetMapping
    public List<Poster> getAllPosters() {
        return postersService.getAllPosters();
    }

    // GET /posters/{id} (Ricerca per ID film) (Invariato)
    @GetMapping("/{id}")
    public ResponseEntity<Poster> getPosterById(@PathVariable Long id) {
        Optional<Poster> poster = postersService.getPosterById(id);
        return poster.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /posters/{id} - L'ID deve essere fornito nel path per la creazione (ID film) (Modificato)
    @PostMapping("/{id}")
    public Poster createPoster(@PathVariable Long id, @RequestBody PosterDTO posterDto) {
        return postersService.createPoster(id, posterDto);
    }

    // PUT /posters/{id} (Ora usa DTO)
    @PutMapping("/{id}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long id, @RequestBody PosterDTO updatedPosterDto) {
        Poster result = postersService.updatePoster(id, updatedPosterDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /posters/{id} (Invariato)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        boolean deleted = postersService.deletePoster(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}