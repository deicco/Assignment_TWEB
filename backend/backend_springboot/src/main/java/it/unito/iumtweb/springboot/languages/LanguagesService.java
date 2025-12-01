package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguagesService {

    @Autowired
    private LanguagesRepository languagesRepository;

    // READ: Ottieni tutte le lingue
    public List<Languages> getAllLanguages() {
        return languagesRepository.findAll();
    }

    // READ: Ottieni una lingua per ID
    public Optional<Languages> getLanguageById(int id) {
        return languagesRepository.findById(id);
    }

    // CREATE/UPDATE: Salva o aggiorna una lingua
    public Languages saveLanguage(Languages language) {
        return languagesRepository.save(language);
    }

    // UPDATE: Aggiorna una lingua esistente
    public Languages updateLanguage(int id, Languages updatedLanguage) {
        return languagesRepository.findById(id)
                .map(existingLanguage -> {
                    existingLanguage.setType(updatedLanguage.getType());
                    existingLanguage.setLanguage(updatedLanguage.getLanguage());
                    return languagesRepository.save(existingLanguage);
                })
                .orElse(null);
    }

    // DELETE: Elimina una lingua per ID
    public boolean deleteLanguage(int id) {
        if (languagesRepository.existsById(id)) {
            languagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}