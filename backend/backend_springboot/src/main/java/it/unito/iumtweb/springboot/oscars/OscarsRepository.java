package it.unito.iumtweb.springboot.oscars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OscarsRepository extends JpaRepository<Oscars, Integer> {

    // Esempio: trovare tutte le nomination/premi per un film specifico
    List<Oscars> findByFilmContainingIgnoreCase(String filmName);
}