package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing Genre resources.
 * <p>
 * Exposes endpoints to query film genres.
 * Supports pagination and search to ensure scalability.
 * </p>
 */
@RestController
@RequestMapping("/genres")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class GenresController {

    @Autowired
    private GenresService genresService;

    /**
     * GET /genres
     * Retrieves all genre associations with pagination and optional filtering.
     *
     * @param page    Page number (default 0).
     * @param size    Items per page (default 20).
     * @param keyword Optional search keyword for genre name.
     * @return A {@link Page} of {@link Genres}.
     */
    @GetMapping
    public ResponseEntity<Page<Genres>> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(genresService.searchGenres(keyword, pageable));
        }
        return ResponseEntity.ok(genresService.getAllGenres(pageable));
    }

    /**
     * GET /genres/movie/{id}
     * Retrieves all genres for a specific movie.
     *
     * @param id The movie ID.
     * @return List of {@link Genres}.
     */
    @GetMapping("/movie/{id}")
    public List<Genres> getGenresByMovie(@PathVariable Long id) {
        return genresService.getGenresByMovieId(id);
    }

    /**
     * POST /genres
     * Creates a new genre association.
     *
     * @param dto The data to create.
     * @return The created entity.
     */
    @PostMapping
    public Genres createGenre(@RequestBody GenresDTO dto) {
        return genresService.createGenre(dto);
    }

    /**
     * DELETE /genres/{id}/{genre}
     * Deletes a genre association.
     *
     * @param id    The movie ID.
     * @param genre The genre name.
     * @return 204 No Content or 404 Not Found.
     */
    @DeleteMapping("/{id}/{genre}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id, @PathVariable String genre) {
        return genresService.deleteGenre(id, genre) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}