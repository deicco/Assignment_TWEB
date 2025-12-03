package it.unito.iumtweb.springboot.themes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Theme resources.
 * <p>
 * Exposes endpoints to query film themes.
 * Supports pagination and search.
 * </p>
 */
@RestController
@RequestMapping("/themes")
@CrossOrigin(origins = "http://localhost:3000")
public class ThemesController {

    @Autowired
    private ThemesService themesService;

    /**
     * GET /themes
     * Retrieves all themes with pagination and optional search.
     *
     * @param page    Page number (default 0).
     * @param size    Items per page (default 20).
     * @param keyword Optional search keyword.
     * @return A {@link Page} of {@link Themes}.
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
    public List<Themes> getThemesByMovieId(@PathVariable Long movieId) {
        return themesService.getThemesByMovieId(movieId);
    }

    /**
     * GET /themes/{movieId}/{theme}
     * Retrieves a specific association.
     */
    @GetMapping("/{movieId}/{theme}")
    public ResponseEntity<Themes> getThemeByCompositeKey(
            @PathVariable Long movieId,
            @PathVariable String theme) {

        Optional<Themes> result = themesService.getThemeByCompositeKey(movieId, theme);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /themes
     * Creates a new theme.
     */
    @PostMapping
    public Themes createTheme(@RequestBody ThemesDTO themeDto) {
        return themesService.createTheme(themeDto);
    }

    /**
     * DELETE /themes/{movieId}/{theme}
     * Deletes a theme.
     */
    @DeleteMapping("/{movieId}/{theme}")
    public ResponseEntity<Void> deleteTheme(
            @PathVariable Long movieId,
            @PathVariable String theme) {

        boolean deleted = themesService.deleteTheme(movieId, theme);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}