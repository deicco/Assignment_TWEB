package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    // READ: Ottiene i generi con paginazione
    public Page<Genres> getAllGenres(Pageable pageable) {
        return genresRepository.findAll(pageable);
    }

    // READ: Ottiene tutti i generi (utile per il CsvDataLoader)
    public List<Genres> findAll() {
        return genresRepository.findAll();
    }

    // CREATE: Salva un nuovo genere (utile per il CsvDataLoader)
    public Genres saveGenre(Genres genre) {
        return genresRepository.save(genre);
    }
}