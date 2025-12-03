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
 * <p>
 * Exposes endpoints to query film languages.
 * Supports pagination and search to ensure scalability.
 * </p>
 */
@RestController
@RequestMapping("/languages")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class LanguagesController {

    @Autowired
    private LanguagesService languagesService;

    /**
     * GET /languages
     * Retrieves all language associations with pagination and optional filtering.
     *
     * @param page    Page number (default 0).
     * @param size    Items per page (default 20).
     * @param keyword Optional search keyword for language name.
     * @return A {@link Page} of {@link Languages}.
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
     * GET /languages/{movieId}
     * Retrieves the list of languages for a specific film.
     *
     * @param movieId The ID of the film.
     * @return A List of {@link Languages}.
     */
    @GetMapping("/{movieId}")
    public List<Languages> getLanguagesByMovieId(@PathVariable Long movieId) {
        return languagesService.getLanguagesByMovieId(movieId);
    }

    /**
     * POST /languages
     * Creates a new language association.
     *
     * @param languageDto The data to create.
     * @return The created entity.
     */
    @PostMapping
    public Languages createLanguage(@RequestBody LanguagesDTO languageDto) {
        return languagesService.createLanguage(languageDto);
    }

    /**
     * PUT /languages/{movieId}/{language}
     * Updates an existing language association.
     */
    @PutMapping("/{movieId}/{language}")
    public ResponseEntity<Languages> updateLanguage(
            @PathVariable Long movieId,
            @PathVariable String language,
            @RequestBody LanguagesDTO updatedLanguageDto) {

        Languages result = languagesService.updateLanguage(movieId, language, updatedLanguageDto);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /languages/{movieId}/{language}
     * Deletes a specific language association.
     */
    @DeleteMapping("/{movieId}/{language}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long movieId, @PathVariable String language) {
        boolean deleted = languagesService.deleteLanguage(movieId, language);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}