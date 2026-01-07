package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing Poster resources.
 * Provides endpoints for paginated access, movie-specific posters, and CRUD operations.
 */
@RestController
@RequestMapping("/posters")
@CrossOrigin(origins = "http://localhost:3000")
public class PosterController {

    @Autowired
    private PostersService postersService;

    /**
     * GET /posters
     * Retrieves a paginated list of all posters available in the database.
     *
     * @param page Page number (default 0).
     * @param size Items per page (default 20).
     * @return A {@link Page} of {@link Poster} objects.
     */
    @GetMapping
    public Page<Poster> getAllPosters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postersService.getAllPosters(pageable);
    }

    /**
     * GET /posters/{id}
     * Retrieves a single poster by its unique surrogate ID.
     *
     * @param id The Long unique identifier of the poster.
     * @return The {@link Poster} if found, or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Poster> getOne(@PathVariable Long id) {
        return postersService.getPosterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /posters/movie/{movieId}
     * Retrieves all posters associated with a specific movie.
     *
     * @param movieId The Integer ID of the movie.
     * @return A list of {@link Poster} objects linked to the movie.
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Poster>> getPostersByMovie(@PathVariable Integer movieId) {
        List<Poster> posters = postersService.getPostersByMovieId(movieId);
        return ResponseEntity.ok(posters);
    }

    /**
     * POST /posters
     * Creates a new poster entry.
     *
     * @param posterDto Data Transfer Object containing poster details.
     * @return The newly created {@link Poster}.
     */
    @PostMapping
    public Poster createPoster(@RequestBody PosterDTO posterDto) {
        return postersService.createPoster(posterDto);
    }

    /**
     * PUT /posters/{id}
     * Updates the link or information of an existing poster.
     *
     * @param id The Long unique identifier of the poster to update.
     * @param updatedPosterDto The updated data.
     * @return The updated {@link Poster} if successful, or 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long id, @RequestBody PosterDTO updatedPosterDto) {
        return postersService.updatePoster(id, updatedPosterDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /posters/{id}
     * Deletes a poster from the database.
     *
     * @param id The Long unique identifier of the poster.
     * @return 204 No Content if deleted, or 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        return postersService.deletePoster(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}