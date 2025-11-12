package it.unito.iumtweb.springboot.countries;

import it.unito.iumtweb.springboot.movies.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    //get all countries by an id
    public List<String> getCountriesByMovie(Long id) {
        Movies movies = new Movies();
        movies.setId(id);

        List<Country> countries = countryRepository.findByMovie(movies);

        return countries.stream()
                .map(country -> country.getId().getCountry())
                .collect(Collectors.toList());
    }
}