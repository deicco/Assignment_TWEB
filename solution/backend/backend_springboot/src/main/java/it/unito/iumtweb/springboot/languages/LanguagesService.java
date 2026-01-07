package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Language management.
 */
@Service
public class LanguagesService {

    @Autowired
    private LanguagesRepository languagesRepository;

    /**
     * Retrieves all languages with pagination.
     */
    public Page<Languages> getAllLanguages(Pageable pageable) {
        return languagesRepository.findAll(pageable);
    }

    /**
     * Searches for languages by name.
     */
    public Page<Languages> searchLanguages(String keyword, Pageable pageable) {
        return languagesRepository.findByLanguageContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves all languages for a specific movie (Integer ID).
     */
    public List<Languages> getLanguagesByMovieId(Integer movieId) {
        return languagesRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific language by unique ID.
     */
    public Optional<Languages> getLanguageById(Long id) {
        return languagesRepository.findById(id);
    }

    /**
     * Creates a new language association.
     */
    public Languages createLanguage(LanguagesDTO dto) {
        Languages language = new Languages(dto.getMovieId(), dto.getLanguage(), dto.getType());
        return languagesRepository.save(language);
    }

    /**
     * Updates an existing language using unique ID.
     */
    public Optional<Languages> updateLanguage(Long id, LanguagesDTO dto) {
        return languagesRepository.findById(id)
                .map(existing -> {
                    existing.setLanguage(dto.getLanguage());
                    existing.setType(dto.getType());
                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return languagesRepository.save(existing);
                });
    }

    /**
     * Deletes a language by unique ID.
     */
    public boolean deleteLanguage(Long id) {
        if (languagesRepository.existsById(id)) {
            languagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}