package it.unito.iumtweb.springboot.countries;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Ottieni per film
    public List<String> getCountriesByMovie(Long movieId) {
        return countryRepository.findByIdMovieId(movieId)
                .stream()
                .map(c -> c.getId().getCountry()) // Accesso tramite PK
                .collect(Collectors.toList());
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    // Ottieni per chiave composta
    public Optional<Country> getCountryByCompositeId(Long movieId, String country) {
        CountryPrimaryKey pk = new CountryPrimaryKey(movieId, country);
        return countryRepository.findById(pk);
    }

    // Crea
    public Country createCountry(CountriesDTO countryDto) {
        CountryPrimaryKey pk = new CountryPrimaryKey(countryDto.getMovieId(), countryDto.getCountry());
        Country newCountry = new Country();
        newCountry.setId(pk);
        return countryRepository.save(newCountry);
    }

    // Aggiorna (solo check esistenza, PK non modificabile)
    public Optional<Country> updateCountry(Long movieId, String country, CountriesDTO updatedCountryDto) {
        CountryPrimaryKey pk = new CountryPrimaryKey(movieId, country);
        // Poiché non ci sono campi extra, questo metodo verifica solo l'esistenza
        return countryRepository.findById(pk);
    }

    // Elimina
    public boolean deleteCountry(Long movieId, String country) {
        CountryPrimaryKey pk = new CountryPrimaryKey(movieId, country);
        if (countryRepository.existsById(pk)) {
            countryRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    public void saveCountry(Country country) {
        countryRepository.save(country);
    }
}