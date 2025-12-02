package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguagesController {

    @Autowired
    private LanguagesService languagesService;

    // GET /languages
    @GetMapping
    public List<Languages> getAllLanguages() {
        return languagesService.getAllLanguages();
    }

    // GET /languages/{movieId}
    // Restituisce la LISTA delle lingue per quel film
    @GetMapping("/{movieId}")
    public List<Languages> getLanguagesByMovieId(@PathVariable int movieId) {
        return languagesService.getLanguagesByMovieId(movieId);
    }

    // POST /languages
    @PostMapping
    public Languages createLanguage(@RequestBody LanguagesDTO languageDto) {
        return languagesService.createLanguage(languageDto);
    }

    // PUT /languages/{movieId}/{language}
    // Esempio: PUT /languages/1/English
    @PutMapping("/{movieId}/{language}")
    public ResponseEntity<Languages> updateLanguage(
            @PathVariable int movieId,
            @PathVariable String language,
            @RequestBody LanguagesDTO updatedLanguageDto) {

        Languages result = languagesService.updateLanguage(movieId, language, updatedLanguageDto);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /languages/{movieId}/{language}
    // Cancella una lingua specifica. Esempio: DELETE /languages/1/English
    @DeleteMapping("/{movieId}/{language}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable int movieId, @PathVariable String language) {
        boolean deleted = languagesService.deleteLanguage(movieId, language);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}