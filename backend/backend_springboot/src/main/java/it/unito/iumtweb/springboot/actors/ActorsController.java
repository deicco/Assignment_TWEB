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

    @GetMapping
    public List<Actors> getAllActors() {
        return actorsService.getAllActors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actors> getActorById(@PathVariable Long id) {
        Optional<Actors> actor = actorsService.getActorById(id);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Actors createActor(@RequestBody Actors actor) {
        return actorsService.saveActor(actor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actors> updateActor(@PathVariable Long id, @RequestBody Actors updatedActor) {
        Actors result = actorsService.updateActor(id, updatedActor);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        boolean deleted = actorsService.deleteActor(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}