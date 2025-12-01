package it.unito.iumtweb.springboot.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    // Cerca tutte le nazioni associate a un determinato ID film
    List<Country> findByMovieId(Long movieId);
}