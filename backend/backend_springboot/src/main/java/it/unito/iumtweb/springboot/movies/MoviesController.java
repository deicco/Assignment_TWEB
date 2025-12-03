package it.unito.iumtweb.springboot.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST Controller for managing Movie resources.
 * <p>
 * Exposes endpoints to query and manipulate the main movie catalog.
 * Supports advanced filtering and pagination.
 * </p>
 */
@RestController
@RequestMapping("/movies")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class MoviesController {

    @Autowired
    private MoviesService service;

    /**
     * GET /movies
     * Retrieves a list of movies with optional filters.
     *
     * @param name Name filter (partial match).
     * @param minRating Minimum rating filter.
     * @param maxRating Maximum rating filter.
     * @param startDate Start date filter.
     * @param endDate End date filter.
     * @param minMinute Minimum duration filter (e.g., movies longer than 120 mins).
     * @param page Page number (default 0).
     * @param size Items per page (default 20).
     * @return A {@link Page} of {@link Movies}.
     */
    @GetMapping
    public Page<Movies> listMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float maxRating,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Integer minMinute,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getMovies(name, minRating, maxRating, startDate, endDate, minMinute, pageable);
    }

    /**
     * GET /movies/{id}
     * Retrieves a specific movie by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movies> getOne(@PathVariable Long id) {
        return service.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /movies
     * Creates a new movie.
     */
    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody MoviesDTO movieDto) {
        Movies saved = service.createMovie(movieDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * PUT /movies/{id}
     * Updates an existing movie.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movies> update(
            @PathVariable Long id,
            @RequestBody MoviesDTO movieDto
    ) {
        return service.updateMovie(id, movieDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /movies/{id}
     * Deletes a movie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteMovie(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}