package it.unito.iumtweb.springboot.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Country resources.
 * <p>
 * Exposes endpoints to query film production countries.
 * Uses standard REST patterns with unique IDs.
 * </p>
 */
@RestController
@RequestMapping("/countries")
@CrossOrigin(origins = "http://localhost:3000")
public class CountryController {

    @Autowired
    private CountryService countryService;

    /**
     * GET /countries
     * Retrieves all country associations or filters by name.
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
     * GET /countries/{id}
     * Retrieves a specific entry by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable Long id) {
        return countryService.getCountryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /countries/movies/{movieId}
     * Retrieves all country names for a specific movie.
     * Note: movieId is Integer.
     */
    @GetMapping("/movies/{movieId}")
    public List<String> getCountriesByMovieId(@PathVariable Integer movieId) {
        return countryService.getCountriesByMovie(movieId);
    }

    /**
     * POST /countries
     * Creates a new country-movie association.
     */
    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody CountriesDTO countryDto) {
        return ResponseEntity.ok(countryService.createCountry(countryDto));
    }

    /**
     * PUT /countries/{id}
     * Updates an existing association by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody CountriesDTO countryDto) {
        return countryService.updateCountry(id, countryDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /countries/{id}
     * Deletes an association by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        return countryService.deleteCountry(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}