package it.unito.iumtweb.springboot.countries;

import it.unito.iumtweb.springboot.countries.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/movies/{id}")
    public List<String> getAllCountries(@PathVariable Long id) {
        return countryService.getCountriesByMovie(id);
    }
}