package it.unito.iumtweb.springboot.themes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemesRepository extends JpaRepository<Themes, Long> {

    // Metodo per trovare un tema per nome
    Themes findByTheme(String theme);
}