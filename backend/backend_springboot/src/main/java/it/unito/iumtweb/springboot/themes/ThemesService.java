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

    // READ: Ottieni i temi di un film specifico
    public List<Themes> getThemesByMovieId(Long movieId) {
        return themesRepository.findByIdId(movieId);
    }

    // READ: Ottieni un tema specifico per chiave composta
    public Optional<Themes> getThemeByCompositeKey(Long movieId, String theme) {
        ThemesPrimaryKey pk = new ThemesPrimaryKey(movieId, theme);
        return themesRepository.findById(pk);
    }

    // CREATE: Salva un nuovo tema usando il DTO
    public Themes createTheme(ThemesDTO themeDto) {
        // Creiamo la chiave composta
        ThemesPrimaryKey pk = new ThemesPrimaryKey(themeDto.getId(), themeDto.getTheme());

        Themes newTheme = new Themes();
        newTheme.setId(pk);

        return themesRepository.save(newTheme);
    }

    // NOTA: Il metodo UPDATE è stato rimosso.
    // L'entità Themes è composta solo dalla Chiave Primaria.
    // Non puoi aggiornare una chiave primaria. Se devi cambiare tema,
    // devi cancellare quello vecchio e crearne uno nuovo.

    // DELETE: Elimina un tema specifico per chiave composta
    public boolean deleteTheme(Long movieId, String theme) {
        ThemesPrimaryKey pk = new ThemesPrimaryKey(movieId, theme);
        if (themesRepository.existsById(pk)) {
            themesRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Metodo helper per salvataggio diretto (se serve per caricamento dati)
    public Themes saveTheme(Themes theme) {
        return themesRepository.save(theme);
    }
}