package it.unito.iumtweb.springboot.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MoviesService {

    @Autowired
    private MoviesRepository repo;

    /**
     * Recupera una pagina di film, applicando eventualmente un solo filtro fra questi:
     * – name       : titolo che contiene (ignore case)
     * – minRating e maxRating
     * – startDate e endDate
     * Se non passi nessun filtro, restituisce tutto paginato.
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

    public Optional<Movies> getMovieById(int id) {
        return repo.findById(id);
    }

    public Movies createMovie(Movies m) {
        return repo.save(m);
    }

    public Optional<Movies> updateMovie(int id, Movies updated) {
        return repo.findById(id)
                .map(existing -> {
                    updated.setId(existing.getId());
                    return repo.save(updated);
                });
    }

    public boolean deleteMovie(int id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
