package it.unito.iumtweb.springboot.movies;

import it.unito.iumtweb.springboot.poster.PosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for managing Movie resources.
 * <p>
 * Handles paginated listings with posters for the homepage and
 * provides full movie details (including languages) for the detail page.
 * </p>
 */
@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MoviesController {

    @Autowired
    private MoviesService service;

    @Autowired
    private PosterRepository posterRepo;

    /**
     * GET /movies
     * Retrieves a paginated list of movies. Each element is a Map containing
     * the movie object and its primary poster link.
     *
     * @param name Optional name filter.
     * @param minRating Minimum rating filter.
     * @param maxRating Maximum rating filter.
     * @param startYear Start year filter.
     * @param endYear End year filter.
     * @param minMinute Minimum duration filter.
     * @param page Page index (default 0).
     * @param size Items per page (default 20).
     * @return A page of maps with "movie" and "posterLink".
     */
    @GetMapping
    public Page<Map<String, Object>> listMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float maxRating,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) Integer minMinute,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movies> moviesPage = service.getMovies(name, minRating, maxRating, startYear, endYear, minMinute, pageable);

        return moviesPage.map(movie -> {
            // Fetch the poster link using movie ID
            String link = posterRepo.findByMovieId(movie.getId().intValue())
                    .stream()
                    .findFirst()
                    .map(p -> p.getLink())
                    .orElse("");

            return Map.of(
                    "movie", movie,
                    "posterLink", link
            );
        });
    }

    /**
     * GET /movies/{id}
     * Retrieves full details for a specific movie, including posters, cast, and languages.
     *
     * @param id The unique identifier of the movie.
     * @return A ResponseEntity containing the MovieDetailDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDTO> getOne(@PathVariable Long id) {
        return service.getMovieDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /movies
     * Creates a new movie entry.
     *
     * @param movieDto DTO containing movie data.
     * @return The saved movie entity.
     */
    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody MoviesDTO movieDto) {
        Movies saved = service.createMovie(movieDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * PUT /movies/{id}
     * Updates an existing movie entry.
     *
     * @param id The movie ID.
     * @param movieDto Updated data.
     * @return The updated entity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movies> update(@PathVariable Long id, @RequestBody MoviesDTO movieDto) {
        return service.updateMovie(id, movieDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /movies/{id}
     * Deletes a movie entry.
     *
     * @param id The movie ID.
     * @return 204 No Content or 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteMovie(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}