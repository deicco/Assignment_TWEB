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

    // GET /themes
    @GetMapping
    public List<Themes> getAllThemes() {
        return themesService.getAllThemes();
    }

    // GET /themes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Themes> getThemeById(@PathVariable Long id) {
        Optional<Themes> theme = themesService.getThemeById(id);
        return theme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /themes
    @PostMapping
    public Themes createTheme(@RequestBody Themes theme) {
        return themesService.saveTheme(theme);
    }

    // PUT /themes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Themes> updateTheme(@PathVariable Long id, @RequestBody Themes updatedTheme) {
        Themes result = themesService.updateTheme(id, updatedTheme);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /themes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        boolean deleted = themesService.deleteTheme(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}