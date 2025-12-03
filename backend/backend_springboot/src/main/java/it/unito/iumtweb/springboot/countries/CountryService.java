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
 * <p>
 * Handles data retrieval, pagination, and persistence for country-movie associations.
 * </p>
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
     *
     * @param pageable Pagination information.
     * @return A {@link Page} of {@link Country} entities.
     */
    public Page<Country> getAllCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    /**
     * Searches for countries by name with pagination.
     *
     * @param name     The country name (or part of it).
     * @param pageable Pagination information.
     * @return A {@link Page} of matching entities.
     */
    public Page<Country> searchCountries(String name, Pageable pageable) {
        return countryRepository.findByIdCountryContainingIgnoreCase(name, pageable);
    }

    /**
     * Retrieves a list of country names associated with a specific movie.
     *
     * @param movieId The ID of the movie.
     * @return A List of country names (Strings).
     */
    public List<String> getCountriesByMovie(Long movieId) {
        return countryRepository.findByIdMovieId(movieId)
                .stream()
                .map(c -> c.getId().getCountry())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific association by composite ID.
     *
     * @param movieId The ID of the movie.
     * @param country The name of the country.
     * @return An Optional containing the entity if found.
     */
    public Optional<Country> getCountryByCompositeId(Long movieId, String country) {
        CountryPrimaryKey pk = new CountryPrimaryKey(movieId, country);
        return countryRepository.findById(pk);
    }

    /**
     * Creates a new country-movie association.
     *
     * @param countryDto DTO containing data.
     * @return The saved entity.
     */
    public Country createCountry(CountriesDTO countryDto) {
        CountryPrimaryKey pk = new CountryPrimaryKey(countryDto.getMovieId(), countryDto.getCountry());
        Country newCountry = new Country();
        newCountry.setId(pk);
        return countryRepository.save(newCountry);
    }

    /**
     * Updates an existing country association.
     * Since the entity only has PK fields, this effectively acts as a check-exists-and-save.
     *
     * @param movieId The ID of the movie.
     * @param country The country name.
     * @param updatedCountryDto New data.
     * @return The updated entity or empty if not found.
     */
    public Optional<Country> updateCountry(Long movieId, String country, CountriesDTO updatedCountryDto) {
        CountryPrimaryKey pk = new CountryPrimaryKey(movieId, country);
        // Note: Logic is limited as there are no non-key fields to update.
        // Usually, one would delete and recreate if the key needs changing.
        return countryRepository.findById(pk);
    }

    /**
     * Deletes a country association.
     *
     * @param movieId The ID of the movie.
     * @param country The name of the country.
     * @return True if deleted, false if not found.
     */
    public boolean deleteCountry(Long movieId, String country) {
        CountryPrimaryKey pk = new CountryPrimaryKey(movieId, country);
        if (countryRepository.existsById(pk)) {
            countryRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}