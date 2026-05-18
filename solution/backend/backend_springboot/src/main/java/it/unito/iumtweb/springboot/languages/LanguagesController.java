package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Language resources.
 */
@RestController
@RequestMapping("/languages")
@CrossOrigin(origins = "http://localhost:3000")
public class LanguagesController {

    private final LanguagesService languagesService;

    @Autowired
    public LanguagesController(LanguagesService languagesService) {
        this.languagesService = languagesService;
    }
    /**
     * GET /languages
     * List all languages with filters.
     */
    @GetMapping
    public ResponseEntity<Page<Languages>> getAllLanguages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(languagesService.searchLanguages(keyword, pageable));
        }
        return ResponseEntity.ok(languagesService.getAllLanguages(pageable));
    }

    /**
     * GET /languages/movie/{movieId}
     * Get languages for a movie (Integer ID).
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Languages>> getLanguagesByMovieId(@PathVariable Integer movieId) {
        return ResponseEntity.ok(languagesService.getLanguagesByMovieId(movieId));
    }

    /**
     * GET /languages/{id}
     * Get by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Languages> getById(@PathVariable Long id) {
        return languagesService.getLanguageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /languages
     * Create new language.
     */
    @PostMapping
    public ResponseEntity<Languages> createLanguage(@RequestBody LanguagesDTO dto) {
        return ResponseEntity.ok(languagesService.createLanguage(dto));
    }

    /**
     * PUT /languages/{id}
     * Update by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Languages> updateLanguage(@PathVariable Long id, @RequestBody LanguagesDTO dto) {
        return languagesService.updateLanguage(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /languages/{id}
     * Delete by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        return languagesService.deleteLanguage(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}