package it.unito.iumtweb.springboot.languages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Languages} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Long> {

    /**
     * Finds all languages associated with a specific film ID.
     * @param movieId The Integer ID of the film.
     */
    List<Languages> findByMovieId(Integer movieId);

    /**
     * Searches for languages by name (partial match, case-insensitive).
     */
    Page<Languages> findByLanguageContainingIgnoreCase(String language, Pageable pageable);
}