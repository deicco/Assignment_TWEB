package it.unito.iumtweb.springboot.countries;

import it.unito.iumtweb.springboot.movies.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository <Country, CountryPrimaryKey>{

    //find all countries where a film has been produced
    List<Country> findByMovie(Movies movies);
}