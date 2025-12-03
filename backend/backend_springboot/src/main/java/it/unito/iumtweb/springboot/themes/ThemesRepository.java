package it.unito.iumtweb.springboot.themes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Themes} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes methods supporting {@link Pageable} to handle large datasets.
 * </p>
 */
@Repository
public interface ThemesRepository extends JpaRepository<Themes, ThemesPrimaryKey> {

    /**
     * Finds all themes for a specific movie.
     */
    List<Themes> findByIdMovieId(Long movieId);

    /**
     * Searches for themes by name (partial match, case-insensitive) with pagination.
     * <p>
     * Useful for exploring movies by theme (e.g., searching for "Revenge").
     * </p>
     *
     * @param theme    The search keyword.
     * @param pageable Pagination info.
     * @return A {@link Page} of matching entities.
     */
    Page<Themes> findByIdThemeContainingIgnoreCase(String theme, Pageable pageable);
}