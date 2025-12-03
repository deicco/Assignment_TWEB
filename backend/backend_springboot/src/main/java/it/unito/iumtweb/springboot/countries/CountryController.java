package it.unito.iumtweb.springboot.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Country resources.
 * <p>
 * Exposes endpoints to query film production countries.
 * Supports pagination and text search to handle the dataset effectively.
 * </p>
 */
@RestController
@RequestMapping("/countries")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class CountryController {

    @Autowired
    private CountryService countryService;

    /**
     * GET /countries
     * Retrieves all country associations with pagination and optional filtering.
     *
     * @param page    Page number (default 0).
     * @param size    Items per page (default 20).
     * @param keyword Optional search keyword for country name.
     * @return A {@link Page} of {@link Country}.
     */
    @GetMapping
    public ResponseEntity<Page<Country>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(countryService.searchCountries(keyword, pageable));
        }

        return ResponseEntity.ok(countryService.getAllCountries(pageable));
    }

    /**
     * GET /countries/{movieId}/{country}
     * Retrieves a specific association by composite key.
     *
     * @param movieId The ID of the movie.
     * @param country The name of the country.
     * @return 200 OK with entity or 404 Not Found.
     */
    @GetMapping("/{movieId}/{country}")
    public ResponseEntity<Country> getCountryByCompositeId(@PathVariable Long movieId, @PathVariable String country) {
        Optional<Country> result = countryService.getCountryByCompositeId(movieId, country);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /countries/movies/{id}
     * Retrieves all countries associated with a specific movie ID.
     *
     * @param id The movie ID.
     * @return List of country names.
     */
    @GetMapping("/movies/{id}")
    public List<String> getCountriesByMovieId(@PathVariable Long id) {
        return countryService.getCountriesByMovie(id);
    }

    /**
     * POST /countries
     * Creates a new country-movie association.
     *
     * @param countryDto The data to create.
     * @return The created entity.
     */
    @PostMapping
    public Country createCountry(@RequestBody CountriesDTO countryDto) {
        return countryService.createCountry(countryDto);
    }

    /**
     * PUT /countries/{movieId}/{country}
     * Updates an existing association.
     *
     * @param movieId    The movie ID.
     * @param country    The country name.
     * @param countryDto New data.
     * @return 200 OK or 404 Not Found.
     */
    @PutMapping("/{movieId}/{country}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long movieId, @PathVariable String country, @RequestBody CountriesDTO countryDto) {
        Optional<Country> result = countryService.updateCountry(movieId, country, countryDto);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /countries/{movieId}/{country}
     * Deletes an association.
     *
     * @param movieId The movie ID.
     * @param country The country name.
     * @return 204 No Content or 404 Not Found.
     */
    @DeleteMapping("/{movieId}/{country}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long movieId, @PathVariable String country) {
        boolean deleted = countryService.deleteCountry(movieId, country);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}