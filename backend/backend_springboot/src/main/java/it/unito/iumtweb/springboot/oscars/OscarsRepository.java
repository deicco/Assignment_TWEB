package it.unito.iumtweb.springboot.oscars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OscarsRepository extends JpaRepository<Oscars, OscarsPrimaryKey> {

    // Esempio: trovare tutte le nomination/premi per un film specifico (tramite campo nella PK)
    List<Oscars> findByIdFilmContainingIgnoreCase(String filmName);
}