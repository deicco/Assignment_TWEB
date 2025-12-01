package it.unito.iumtweb.springboot.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Endpoint: /countries/movies/123
    @GetMapping("/movies/{id}")
    public List<String> getCountriesByMovieId(@PathVariable Long id) {
        return countryService.getCountriesByMovie(id);
    }
}