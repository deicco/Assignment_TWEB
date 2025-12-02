package it.unito.iumtweb.springboot.languages;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LanguagesRepository extends JpaRepository<Languages, LanguagesPrimaryKey> {

    // CORRETTO: Cerca tutte le lingue che hanno questo ID film.
    // Spring capisce che deve guardare dentro "id" (EmbeddedId) e cercare la proprietà "id" (int).
    List<Languages> findByIdId(int movieId);

    // Se vuoi cancellare tutte le lingue di un film:
    void deleteByIdId(int movieId);

    // Nota: findById(LanguagesPrimaryKey key), existsById(...) e deleteById(...)
    // sono già inclusi in JpaRepository, non serve scriverli qui.
}