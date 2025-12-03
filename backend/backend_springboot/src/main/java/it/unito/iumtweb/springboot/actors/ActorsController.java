package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Actor resources.
 * <p>
 * Exposes HTTP endpoints to allow external systems (e.g., the Main Express Server)
 * to query and modify static actor data stored in PostgreSQL.
 * Supports pagination and search to handle the large dataset.
 * </p>
 */
@RestController
@RequestMapping("/actors")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class ActorsController {

    @Autowired
    private ActorsService actorsService;

    /**
     * GET /actors
     * Retrieves a paginated list of actors. Can also filter by name.
     * <p>
     * Example usage: /actors?page=0&size=20&name=Brad
     * </p>
     *
     * @param page The page number (default 0).
     * @param size The number of items per page (default 20).
     * @param name (Optional) A string to search within actor names.
     * @return A {@link Page} of {@link Actors}.
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
     * GET /actors/movie/{movieId}
     * Retrieves the cast of a specific movie.
     *
     * @param movieId The ID of the movie provided in the path.
     * @return A list of {@link Actors} associated with the movie.
     */
    @GetMapping("/movie/{movieId}")
    public List<Actors> getActorsByMovieId(@PathVariable Long movieId) {
        return actorsService.getActorsByMovieId(movieId);
    }

    /**
     * GET /actors/{movieId}/{actorName}
     * Retrieves a specific actor record by their composite ID.
     *
     * @param movieId   The ID of the movie.
     * @param actorName The name of the actor.
     * @return {@code 200 OK} with the actor if found, or {@code 404 Not Found}.
     */
    @GetMapping("/{movieId}/{actorName}")
    public ResponseEntity<Actors> getActorByCompositeId(@PathVariable Long movieId, @PathVariable String actorName) {
        Optional<Actors> actor = actorsService.getActorByCompositeId(movieId, actorName);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /actors
     * Creates a new actor association.
     *
     * @param actorDto The request body containing actor data.
     * @return The created {@link Actors} entity.
     */
    @PostMapping
    public Actors createActor(@RequestBody ActorsDTO actorDto) {
        return actorsService.createActor(actorDto);
    }

    /**
     * PUT /actors/{movieId}/{actorName}
     * Updates an existing actor's details (e.g., role).
     *
     * @param movieId         The ID of the movie.
     * @param actorName       The name of the actor.
     * @param updatedActorDto The request body containing updated data.
     * @return {@code 200 OK} with the updated entity, or {@code 404 Not Found}.
     */
    @PutMapping("/{movieId}/{actorName}")
    public ResponseEntity<Actors> updateActor(@PathVariable Long movieId, @PathVariable String actorName, @RequestBody ActorsDTO updatedActorDto) {
        Actors result = actorsService.updateActor(movieId, actorName, updatedActorDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /actors/{movieId}/{actorName}
     * Deletes a specific actor association.
     *
     * @param movieId   The ID of the movie.
     * @param actorName The name of the actor.
     * @return {@code 204 No Content} if successful, or {@code 404 Not Found}.
     */
    @DeleteMapping("/{movieId}/{actorName}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long movieId, @PathVariable String actorName) {
        boolean deleted = actorsService.deleteActor(movieId, actorName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}