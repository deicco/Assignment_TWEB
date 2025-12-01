package it.unito.iumtweb.springboot.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class MoviesService {

    @Autowired
    private MoviesRepository repo;

    /**
     * Recupera una pagina di film con filtri
     */
    public Page<Movies> getMovies(
            String name,
            Float minRating,
            Float maxRating,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        if (name != null && !name.isBlank()) {
            return repo.findByNameContainingIgnoreCase(name, pageable);
        }
        if (minRating != null && maxRating != null) {
            return repo.findByRatingBetween(minRating, maxRating, pageable);
        }
        if (startDate != null && endDate != null) {
            return repo.findByDateBetween(startDate, endDate, pageable);
        }
        return repo.findAll(pageable);
    }

    // Metodo aggiornato: da int a Long
    public Optional<Movies> getMovieById(Long id) {
        return repo.findById(id);
    }

    public Movies createMovie(Movies m) {
        return repo.save(m);
    }

    // Metodo aggiornato: da int a Long
    public Optional<Movies> updateMovie(Long id, Movies updated) {
        return repo.findById(id)
                .map(existing -> {
                    // Mantiene l'ID esistente
                    updated.setId(existing.getId());
                    return repo.save(updated);
                });
    }

    // Metodo aggiornato: da int a Long
    public boolean deleteMovie(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    // Aggiunto per il CsvDataLoader
    public List<Movies> findAll() {
        return repo.findAll();
    }
}