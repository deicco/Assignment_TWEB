package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OscarsService {

    @Autowired
    private OscarsRepository oscarsRepository;

    // READ: Ottieni tutte le nomination
    public List<Oscars> getAllOscars() {
        return oscarsRepository.findAll();
    }

    // READ: Ottieni nomination per l'anno del film (PK)
    public Optional<Oscars> getOscarByFilmYear(int year_film) {
        return oscarsRepository.findById(year_film);
    }

    // READ: Cerca nomination per nome del film
    public List<Oscars> getOscarsByFilm(String filmName) {
        return oscarsRepository.findByFilmContainingIgnoreCase(filmName);
    }

    // CREATE/UPDATE: Salva o aggiorna una nomination
    public Oscars saveOscar(Oscars oscar) {
        return oscarsRepository.save(oscar);
    }

    // DELETE: Elimina una nomination per anno del film (PK)
    public boolean deleteOscar(int year_film) {
        if (oscarsRepository.existsById(year_film)) {
            oscarsRepository.deleteById(year_film);
            return true;
        }
        return false;
    }
}