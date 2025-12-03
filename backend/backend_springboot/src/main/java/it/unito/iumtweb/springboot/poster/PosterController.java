package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST Controller for managing Poster resources.
 * <p>
 * Exposes endpoints to retrieve and modify movie poster links.
 * </p>
 */
@RestController
@RequestMapping("/posters")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class PosterController {

    @Autowired
    private PostersService postersService;

    /**
     * GET /posters
     * Retrieves a paginated list of all posters.
     *
     * @param page Page number (default 0).
     * @param size Items per page (default 20).
     * @return A {@link Page} of {@link Poster}.
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
     * Retrieves the poster for a specific Movie ID.
     *
     * @param id The Movie ID.
     * @return 200 OK with Poster or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Poster> getPosterById(@PathVariable Long id) {
        Optional<Poster> poster = postersService.getPosterById(id);
        return poster.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /posters/{id}
     * Creates a new poster for a specific Movie ID.
     *
     * @param id The Movie ID.
     * @param posterDto The poster data.
     * @return The created Poster.
     */
    @PostMapping("/{id}")
    public Poster createPoster(@PathVariable Long id, @RequestBody PosterDTO posterDto) {
        return postersService.createPoster(id, posterDto);
    }

    /**
     * PUT /posters/{id}
     * Updates an existing poster link.
     *
     * @param id The Movie ID.
     * @param updatedPosterDto The new data.
     * @return 200 OK or 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long id, @RequestBody PosterDTO updatedPosterDto) {
        Poster result = postersService.updatePoster(id, updatedPosterDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /posters/{id}
     * Deletes a poster.
     *
     * @param id The Movie ID.
     * @return 204 No Content or 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        boolean deleted = postersService.deletePoster(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}