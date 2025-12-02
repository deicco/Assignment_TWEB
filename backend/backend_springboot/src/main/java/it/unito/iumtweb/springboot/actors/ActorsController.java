package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorsController {

    @Autowired
    private ActorsService actorsService;

    // GET /actors
    @GetMapping
    public List<Actors> getAllActors() {
        return actorsService.getAllActors();
    }

    // GET /actors/movie/{movieId} (Nuovo)
    @GetMapping("/movie/{movieId}")
    public List<Actors> getActorsByMovieId(@PathVariable Long movieId) {
        return actorsService.getActorsByMovieId(movieId);
    }

    // GET /actors/{movieId}/{actorName} (Ottieni un attore specifico tramite chiave composta)
    @GetMapping("/{movieId}/{actorName}")
    public ResponseEntity<Actors> getActorByCompositeId(@PathVariable Long movieId, @PathVariable String actorName) {
        Optional<Actors> actor = actorsService.getActorByCompositeId(movieId, actorName);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /actors (Ora usa DTO)
    @PostMapping
    public Actors createActor(@RequestBody ActorsDTO actorDto) {
        return actorsService.createActor(actorDto);
    }

    // PUT /actors/{movieId}/{actorName} (Aggiorna con chiave composta e DTO)
    @PutMapping("/{movieId}/{actorName}")
    public ResponseEntity<Actors> updateActor(@PathVariable Long movieId, @PathVariable String actorName, @RequestBody ActorsDTO updatedActorDto) {
        Actors result = actorsService.updateActor(movieId, actorName, updatedActorDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /actors/{movieId}/{actorName} (Elimina con chiave composta)
    @DeleteMapping("/{movieId}/{actorName}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long movieId, @PathVariable String actorName) {
        boolean deleted = actorsService.deleteActor(movieId, actorName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}