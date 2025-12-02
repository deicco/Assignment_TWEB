package it.unito.iumtweb.springboot.themes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ThemesRepository extends JpaRepository<Themes, ThemesPrimaryKey> {

    // Trova tutti i temi di un film (cerca per la parte 'id' della chiave composta)
    List<Themes> findByIdId(Long movieId);
}