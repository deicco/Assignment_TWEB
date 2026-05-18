package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Actor resources.
 * <p>
 * Exposes endpoints to query, create, update, and delete actors.
 * Uses the unique ID for specific resource manipulation.
 * </p>
 */
@RestController
@RequestMapping("/actors")
@CrossOrigin(origins = "http://localhost:3000")
public class ActorsController {

    private final ActorsService actorsService;

    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    /**
     * GET /actors
     * Retrieves a paginated list of actors, optionally filtered by name.
     */
    @GetMapping
    public ResponseEntity<Page<Actors>> getAllActors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);

        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(actorsService.searchActorsByName(name, pageable));
        }
        return ResponseEntity.ok(actorsService.getAllActors(pageable));
    }

    /**
     * GET /actors/{id}
     * Retrieves a specific actor by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Actors> getById(@PathVariable Long id) {
        return actorsService.getActorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /actors/movie/{movieId}
     * Retrieves the cast of a specific movie.
     * Note: movieId is Integer to match Movies entity.
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Actors>> getActorsByMovieId(@PathVariable Integer movieId) {
        List<Actors> actors = actorsService.getActorsByMovieId(movieId);
        return ResponseEntity.ok(actors);
    }

    /**
     * POST /actors
     * Creates a new actor.
     */
    @PostMapping
    public ResponseEntity<Actors> createActor(@RequestBody ActorsDTO actorDto) {
        Actors saved = actorsService.createActor(actorDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * PUT /actors/{id}
     * Updates an actor by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Actors> updateActor(@PathVariable Long id, @RequestBody ActorsDTO dto) {
        return actorsService.updateActor(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /actors/{id}
     * Deletes an actor by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        return actorsService.deleteActor(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}