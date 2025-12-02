package it.unito.iumtweb.springboot.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Endpoint: GET /countries (Tutte le associazioni)
    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAll();
    }

    // Endpoint: GET /countries/{movieId}/{country} (Associazione per PK)
    @GetMapping("/{movieId}/{country}")
    public ResponseEntity<Country> getCountryByCompositeId(@PathVariable Long movieId, @PathVariable String country) {
        Optional<Country> result = countryService.getCountryByCompositeId(movieId, country);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint: GET /countries/movies/123 (Nazioni associate a un film)
    @GetMapping("/movies/{id}")
    public List<String> getCountriesByMovieId(@PathVariable Long id) {
        return countryService.getCountriesByMovie(id);
    }

    // Endpoint: POST /countries (Crea una nuova associazione)
    @PostMapping
    public Country createCountry(@RequestBody CountriesDTO countryDto) {
        return countryService.createCountry(countryDto);
    }

    // Endpoint: PUT /countries/{movieId}/{country} (Aggiorna un'associazione per PK)
    @PutMapping("/{movieId}/{country}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long movieId, @PathVariable String country, @RequestBody CountriesDTO countryDto) {
        Optional<Country> result = countryService.updateCountry(movieId, country, countryDto);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint: DELETE /countries/{movieId}/{country} (Elimina un'associazione per PK)
    @DeleteMapping("/{movieId}/{country}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long movieId, @PathVariable String country) {
        boolean deleted = countryService.deleteCountry(movieId, country);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}