package it.unito.iumtweb.springboot.countries;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<String> getCountriesByMovie(Long movieId) {
        return countryRepository.findByMovieId(movieId)
                .stream()
                .map(Country::getCountry)
                .collect(Collectors.toList());
    }

    // Metodo utile per il caricamento dati
    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }
}