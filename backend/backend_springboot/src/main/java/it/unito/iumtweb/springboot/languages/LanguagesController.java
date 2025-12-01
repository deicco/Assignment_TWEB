package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // GET /languages/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Languages> getLanguageById(@PathVariable int id) {
        Optional<Languages> language = languagesService.getLanguageById(id);
        return language.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /languages
    @PostMapping
    public Languages createLanguage(@RequestBody Languages language) {
        return languagesService.saveLanguage(language);
    }

    // PUT /languages/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Languages> updateLanguage(@PathVariable int id, @RequestBody Languages updatedLanguage) {
        Languages result = languagesService.updateLanguage(id, updatedLanguage);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /languages/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable int id) {
        boolean deleted = languagesService.deleteLanguage(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}