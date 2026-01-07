package it.unito.iumtweb.springboot.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class encapsulating business logic for Country management.
 */
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Retrieves all country entries with pagination.
     */
    public Page<Country> getAllCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    /**
     * Searches for countries by name with pagination.
     */
    public Page<Country> searchCountries(String name, Pageable pageable) {
        return countryRepository.findByCountryContainingIgnoreCase(name, pageable);
    }

    /**
     * Retrieves a list of country names associated with a specific movie.
     * @param movieId The Integer ID of the movie.
     */
    public List<String> getCountriesByMovie(Integer movieId) {
        return countryRepository.findByMovieId(movieId)
                .stream()
                .map(Country::getCountry)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a country entry by its unique ID.
     */
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    /**
     * Creates a new country-movie association.
     */
    public Country createCountry(CountriesDTO dto) {
        Country newCountry = new Country(dto.getMovieId(), dto.getCountry());
        return countryRepository.save(newCountry);
    }

    /**
     * Updates an existing country association using unique ID.
     */
    public Optional<Country> updateCountry(Long id, CountriesDTO dto) {
        return countryRepository.findById(id)
                .map(existing -> {
                    existing.setCountry(dto.getCountry());
                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return countryRepository.save(existing);
                });
    }

    /**
     * Deletes a country association by unique ID.
     */
    public boolean deleteCountry(Long id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}