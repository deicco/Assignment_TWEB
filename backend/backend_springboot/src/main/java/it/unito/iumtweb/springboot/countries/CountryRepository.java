package it.unito.iumtweb.springboot.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, CountryPrimaryKey> {

    // Spring Data cercherà dentro "id" (PK) il campo "MovieId". Ora esiste!
    List<Country> findByIdMovieId(Long movieId);

    // Cerca per country
    List<Country> findByIdCountry(String country);
}