package it.unito.iumtweb.springboot.languages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Languages} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes methods supporting {@link Pageable} to handle large datasets efficiently.
 * </p>
 */
@Repository
public interface LanguagesRepository extends JpaRepository<Languages, LanguagesPrimaryKey> {

    /**
     * Finds all languages associated with a specific film ID.
     *
     * @param movieId The ID of the film.
     * @return A list of {@link Languages}.
     */
    List<Languages> findByIdMovieId(Long movieId);

    /**
     * Searches for languages by name (partial match, case-insensitive) with pagination.
     * <p>
     * Useful for exploring movies by language (e.g., searching for "French").
     * </p>
     *
     * @param language The search keyword.
     * @param pageable The pagination info.
     * @return A {@link Page} of matching entities.
     */
    Page<Languages> findByIdLanguageContainingIgnoreCase(String language, Pageable pageable);

    /**
     * Deletes all languages associated with a specific movie ID.
     *
     * @param movieId The ID of the film.
     */
    void deleteByIdMovieId(Long movieId);
}