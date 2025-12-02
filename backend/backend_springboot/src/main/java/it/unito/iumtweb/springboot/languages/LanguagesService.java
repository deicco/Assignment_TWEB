package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LanguagesService {

    @Autowired
    private LanguagesRepository languagesRepository;

    // READ: Ottieni tutte le lingue (di tutti i film)
    public List<Languages> getAllLanguages() {
        return languagesRepository.findAll();
    }

    // READ: Ottieni tutte le lingue di uno specifico film
    public List<Languages> getLanguagesByMovieId(int movieId) {
        // Usiamo il metodo corretto definito nel repository
        return languagesRepository.findByIdId(movieId);
    }

    // READ: Ottieni una lingua specifica (Chiave composta)
    public Optional<Languages> getLanguageByCompositeKey(int movieId, String language) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(movieId, language);
        return languagesRepository.findById(pk);
    }

    // CREATE
    public Languages createLanguage(LanguagesDTO languageDto) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(languageDto.getId(), languageDto.getLanguage());
        Languages newLanguage = new Languages();
        newLanguage.setId(pk);
        newLanguage.setType(languageDto.getType());
        return languagesRepository.save(newLanguage);
    }

    // UPDATE
    public Languages updateLanguage(int movieId, String language, LanguagesDTO updatedLanguageDto) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(movieId, language);

        return languagesRepository.findById(pk)
                .map(existingLanguage -> {
                    existingLanguage.setType(updatedLanguageDto.getType());
                    return languagesRepository.save(existingLanguage);
                })
                .orElse(null);
    }

    // DELETE (Specifica: cancella SOLO quella lingua per quel film)
    public boolean deleteLanguage(int movieId, String language) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(movieId, language);
        if (languagesRepository.existsById(pk)) {
            languagesRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // DELETE (Generica: cancella tutte le lingue di un film)
    @Transactional // Necessario per le delete massive
    public boolean deleteAllLanguagesByMovieId(int movieId) {
        List<Languages> list = languagesRepository.findByIdId(movieId);
        if(!list.isEmpty()){
            languagesRepository.deleteByIdId(movieId);
            return true;
        }
        return false;
    }
}