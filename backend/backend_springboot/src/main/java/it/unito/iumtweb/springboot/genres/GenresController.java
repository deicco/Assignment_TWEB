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
 */
@RestController
@RequestMapping("/genres")
@CrossOrigin(origins = "http://localhost:3000")
public class GenresController {

    @Autowired
    private GenresService genresService;

    /**
     * GET /genres
     * List all genres with filters.
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
     * Get genres for a movie (Integer ID).
     */
    @GetMapping("/movie/{movieId}")
    public List<Genres> getGenresByMovie(@PathVariable Integer movieId) {
        return genresService.getGenresByMovieId(movieId);
    }

    /**
     * GET /genres/{id}
     * Get by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genres> getById(@PathVariable Long id) {
        return genresService.getGenreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /genres
     * Create new genre.
     */
    @PostMapping
    public ResponseEntity<Genres> createGenre(@RequestBody GenresDTO dto) {
        return ResponseEntity.ok(genresService.createGenre(dto));
    }

    /**
     * PUT /genres/{id}
     * Update by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Genres> updateGenre(@PathVariable Long id, @RequestBody GenresDTO dto) {
        return genresService.updateGenre(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /genres/{id}
     * Delete by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        return genresService.deleteGenre(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}