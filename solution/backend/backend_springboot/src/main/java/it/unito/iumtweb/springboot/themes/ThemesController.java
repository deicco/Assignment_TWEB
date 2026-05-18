package it.unito.iumtweb.springboot.themes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Theme resources.
 */
@RestController
@RequestMapping("/themes")
@CrossOrigin(origins = "http://localhost:3000")
public class ThemesController {

    private final ThemesService themesService;

    @Autowired
    public ThemesController(ThemesService themesService) {
        this.themesService = themesService;
    }

    /**
     * GET /themes
     * Retrieves all themes with pagination and optional search.
     */
    @GetMapping
    public ResponseEntity<Page<Themes>> getAllThemes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(themesService.searchThemes(keyword, pageable));
        }
        return ResponseEntity.ok(themesService.getAllThemes(pageable));
    }

    /**
     * GET /themes/movie/{movieId}
     * Retrieves all themes for a specific movie.
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Themes>> getThemesByMovieId(@PathVariable Integer movieId) {
        return ResponseEntity.ok(themesService.getThemesByMovieId(movieId));
    }

    /**
     * GET /themes/{id}
     * Retrieves a specific theme by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Themes> getById(@PathVariable Long id) {
        return themesService.getThemeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /themes
     * Creates a new theme.
     */
    @PostMapping
    public ResponseEntity<Themes> createTheme(@RequestBody ThemesDTO themeDto) {
        return ResponseEntity.ok(themesService.createTheme(themeDto));
    }
    /**
     * PUT /themes/{id}
     * Updates an existing theme by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Themes> updateTheme(@PathVariable Long id, @RequestBody ThemesDTO dto) {
        return themesService.updateTheme(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /themes/{id}
     * Deletes a theme by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        return themesService.deleteTheme(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}