package it.unito.iumtweb.springboot.themes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/themes")
public class ThemesController {

    @Autowired
    private ThemesService themesService;

    // GET /themes (Tutti i temi)
    @GetMapping
    public List<Themes> getAllThemes() {
        return themesService.getAllThemes();
    }

    // GET /themes/movie/{movieId} (Temi di un film specifico)
    @GetMapping("/movie/{movieId}")
    public List<Themes> getThemesByMovieId(@PathVariable Long movieId) {
        return themesService.getThemesByMovieId(movieId);
    }

    // GET /themes/{movieId}/{theme} (Tema specifico per chiave composta)
    @GetMapping("/{movieId}/{theme}")
    public ResponseEntity<Themes> getThemeByCompositeKey(
            @PathVariable Long movieId,
            @PathVariable String theme) {

        Optional<Themes> result = themesService.getThemeByCompositeKey(movieId, theme);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /themes (Crea nuovo tema)
    @PostMapping
    public Themes createTheme(@RequestBody ThemesDTO themeDto) {
        return themesService.createTheme(themeDto);
    }

    // DELETE /themes/{movieId}/{theme} (Cancella tema specifico)
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