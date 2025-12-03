package it.unito.iumtweb.springboot.themes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Theme management.
 * <p>
 * Handles data retrieval, pagination, and persistence.
 * </p>
 */
@Service
public class ThemesService {

    @Autowired
    private ThemesRepository themesRepository;

    /**
     * Retrieves all themes with pagination.
     * <p>
     * <b>Mandatory:</b> Prevents OutOfMemory errors.
     * </p>
     */
    public Page<Themes> getAllThemes(Pageable pageable) {
        return themesRepository.findAll(pageable);
    }

    /**
     * Searches for themes by name with pagination.
     */
    public Page<Themes> searchThemes(String keyword, Pageable pageable) {
        return themesRepository.findByIdThemeContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves themes by movie ID.
     */
    public List<Themes> getThemesByMovieId(Long movieId) {
        return themesRepository.findByIdMovieId(movieId);
    }

    /**
     * Retrieves a specific theme by composite key.
     */
    public Optional<Themes> getThemeByCompositeKey(Long movieId, String theme) {
        ThemesPrimaryKey pk = new ThemesPrimaryKey(movieId, theme);
        return themesRepository.findById(pk);
    }

    /**
     * Creates a new theme association.
     */
    public Themes createTheme(ThemesDTO themeDto) {
        ThemesPrimaryKey pk = new ThemesPrimaryKey(themeDto.getMovieId(), themeDto.getTheme());
        Themes newTheme = new Themes();
        newTheme.setId(pk);
        return themesRepository.save(newTheme);
    }

    /**
     * Deletes a theme association.
     */
    public boolean deleteTheme(Long movieId, String theme) {
        ThemesPrimaryKey pk = new ThemesPrimaryKey(movieId, theme);
        if (themesRepository.existsById(pk)) {
            themesRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}