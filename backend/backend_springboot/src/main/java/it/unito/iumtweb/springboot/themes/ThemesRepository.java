package it.unito.iumtweb.springboot.themes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Themes} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface ThemesRepository extends JpaRepository<Themes, Long> {

    /**
     * Finds all themes for a specific movie ID.
     * @param movieId The Integer ID of the film.
     */
    List<Themes> findByMovieId(Integer movieId);

    /**
     * Searches for themes by name (partial match, case-insensitive).
     */
    Page<Themes> findByThemeContainingIgnoreCase(String theme, Pageable pageable);
}