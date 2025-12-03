package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Language management.
 * <p>
 * Handles data retrieval, pagination, and persistence for language-movie associations.
 * </p>
 */
@Service
public class LanguagesService {

    @Autowired
    private LanguagesRepository languagesRepository;

    /**
     * Retrieves all language entries with pagination.
     *
     * @param pageable Pagination information.
     * @return A {@link Page} of {@link Languages}.
     */
    public Page<Languages> getAllLanguages(Pageable pageable) {
        return languagesRepository.findAll(pageable);
    }

    /**
     * Searches for languages by name with pagination.
     *
     * @param keyword  The language name keyword.
     * @param pageable Pagination information.
     * @return A {@link Page} of matching entities.
     */
    public Page<Languages> searchLanguages(String keyword, Pageable pageable) {
        return languagesRepository.findByIdLanguageContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves all languages for a specific movie.
     * @param movieId The movie ID.
     * @return List of languages.
     */
    public List<Languages> getLanguagesByMovieId(Long movieId) {
        return languagesRepository.findByIdMovieId(movieId);
    }

    /**
     * Retrieves a specific language entry using the composite key.
     * @param movieId  The movie ID.
     * @param language The language name.
     * @return An Optional with the entity.
     */
    public Optional<Languages> getLanguageByCompositeKey(Long movieId, String language) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(movieId, language);
        return languagesRepository.findById(pk);
    }

    /**
     * Creates a new language association.
     * @param dto The data transfer object.
     * @return The saved entity.
     */
    public Languages createLanguage(LanguagesDTO dto) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(dto.getMovieId(), dto.getLanguage());
        Languages newLanguage = new Languages();
        newLanguage.setId(pk);
        newLanguage.setType(dto.getType());
        return languagesRepository.save(newLanguage);
    }

    /**
     * Updates an existing language association.
     */
    public Languages updateLanguage(Long movieId, String language, LanguagesDTO updatedLanguageDto) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(movieId, language);
        return languagesRepository.findById(pk)
                .map(existingLanguage -> {
                    existingLanguage.setType(updatedLanguageDto.getType());
                    return languagesRepository.save(existingLanguage);
                })
                .orElse(null);
    }

    /**
     * Deletes a specific language association.
     */
    public boolean deleteLanguage(Long movieId, String language) {
        LanguagesPrimaryKey pk = new LanguagesPrimaryKey(movieId, language);
        if (languagesRepository.existsById(pk)) {
            languagesRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    /**
     * Deletes all languages for a specific movie.
     */
    @Transactional
    public boolean deleteAllLanguagesByMovieId(Long movieId) {
        List<Languages> list = languagesRepository.findByIdMovieId(movieId);
        if (!list.isEmpty()) {
            languagesRepository.deleteByIdMovieId(movieId);
            return true;
        }
        return false;
    }
}