package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing Poster resources.
 */
@RestController
@RequestMapping("/posters")
@CrossOrigin(origins = "http://localhost:3000")
public class PosterController {

    @Autowired
    private PostersService postersService;

    /**
     * GET /posters
     * Retrieves a paginated list of all posters.
     */
    @GetMapping
    public Page<Poster> getAllPosters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postersService.getAllPosters(pageable);
    }

    /**
     * GET /posters/movie/{movieId}
     * Retrieves the poster for a specific Movie ID.
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Poster> getPosterByMovieId(@PathVariable Integer movieId) {
        return postersService.getPosterByMovieId(movieId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /posters/{id}
     * Retrieves the poster by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Poster> getById(@PathVariable Long id) {
        return postersService.getPosterById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /posters
     * Creates a new poster.
     */
    @PostMapping
    public Poster createPoster(@RequestBody PosterDTO posterDto) {
        return postersService.createPoster(posterDto);
    }

    /**
     * PUT /posters/{id}
     * Updates an existing poster link by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long id, @RequestBody PosterDTO updatedPosterDto) {
        return postersService.updatePoster(id, updatedPosterDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /posters/{id}
     * Deletes a poster by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        return postersService.deletePoster(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}