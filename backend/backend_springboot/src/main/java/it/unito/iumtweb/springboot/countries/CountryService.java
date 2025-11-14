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

    public List<String> getCountriesByMovie(Long id) {
        return countryRepository.findByMovieId(id)
                .stream()
                .map(Country::getCountry)
                .collect(Collectors.toList());
    }
}
