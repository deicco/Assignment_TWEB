package it.unito.iumtweb.springboot.themes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemesService {

    @Autowired
    private ThemesRepository themesRepository;

    // READ: Ottieni tutti i temi
    public List<Themes> getAllThemes() {
        return themesRepository.findAll();
    }

    // READ: Ottieni un tema per ID
    public Optional<Themes> getThemeById(Long id) {
        return themesRepository.findById(id);
    }

    // CREATE/UPDATE: Salva o aggiorna un tema
    public Themes saveTheme(Themes theme) {
        return themesRepository.save(theme);
    }

    // UPDATE: Aggiorna un tema esistente
    public Themes updateTheme(Long id, Themes updatedTheme) {
        return themesRepository.findById(id)
                .map(existingTheme -> {
                    existingTheme.setTheme(updatedTheme.getTheme());
                    return themesRepository.save(existingTheme);
                })
                .orElse(null);
    }

    // DELETE: Elimina un tema per ID
    public boolean deleteTheme(Long id) {
        if (themesRepository.existsById(id)) {
            themesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}