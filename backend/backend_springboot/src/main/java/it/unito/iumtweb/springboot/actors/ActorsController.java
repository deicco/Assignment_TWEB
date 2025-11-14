package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.unito.iumtweb.springboot.actors.ActorsRepository;
import it.unito.iumtweb.springboot.actors.ActorsService;
import it.unito.iumtweb.springboot.actors.Actors;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorsController {

    @Autowired
    private ActorsRepository actorsRepository;

    @GetMapping
    public List<Actors> getAllActors() {
        return actorsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actors> getActorById(@PathVariable int id) {
        Optional<Actors> actor = actorsRepository.findById(id);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Actors createActor(@RequestBody Actors actor) {
        return actorsRepository.save(actor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actors> updateActor(@PathVariable int id, @RequestBody Actors updatedActor) {
        return actorsRepository.findById(id)
                .map(existingActor -> {
                    existingActor.setName(updatedActor.getName());
                    existingActor.setRole(updatedActor.getRole());
                    return ResponseEntity.ok(actorsRepository.save(existingActor));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable int id) {
        if (actorsRepository.existsById(id)) {
            actorsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
