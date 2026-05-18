package it.unito.iumtweb.springboot.themes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Theme management.
 */
@Service
public class ThemesService {

    private final ThemesRepository themesRepository;

    @Autowired
    public ThemesService(ThemesRepository themesRepository) {
        this.themesRepository = themesRepository;
    }
    /**
     * Retrieves all themes with pagination.
     */
    public Page<Themes> getAllThemes(Pageable pageable) {
        return themesRepository.findAll(pageable);
    }

    /**
     * Searches for themes by name.
     */
    public Page<Themes> searchThemes(String keyword, Pageable pageable) {
        return themesRepository.findByThemeContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves themes by movie ID (Integer).
     */
    public List<Themes> getThemesByMovieId(Integer movieId) {
        return themesRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific theme by unique ID.
     */
    public Optional<Themes> getThemeById(Long id) {
        return themesRepository.findById(id);
    }

    /**
     * Creates a new theme association.
     */
    public Themes createTheme(ThemesDTO themeDto) {
        Themes newTheme = new Themes(themeDto.getMovieId(), themeDto.getTheme());
        return themesRepository.save(newTheme);
    }

    /**
     * Updates an existing theme by unique ID.
     */
    public Optional<Themes> updateTheme(Long id, ThemesDTO dto) {
        return themesRepository.findById(id)
                .map(existing -> {
                    existing.setTheme(dto.getTheme());
                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return themesRepository.save(existing);
                });
    }

    /**
     * Deletes a theme association by unique ID.
     */
    public boolean deleteTheme(Long id) {
        if (themesRepository.existsById(id)) {
            themesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}