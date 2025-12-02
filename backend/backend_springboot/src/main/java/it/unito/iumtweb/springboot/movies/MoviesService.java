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
     * Recupera una pagina di film con filtri (Invariato)
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

    // Metodo aggiornato: da int a Long (Invariato)
    public Optional<Movies> getMovieById(Long id) {
        return repo.findById(id);
    }

    // CREATE: Crea un nuovo film usando il DTO (Modificato)
    public Movies createMovie(MoviesDTO movieDto) {
        Movies newMovie = new Movies();

        // Mappatura DTO -> Entity
        newMovie.setName(movieDto.getName());
        newMovie.setDate(movieDto.getDate());
        newMovie.setTagline(movieDto.getTagline());
        newMovie.setDescription(movieDto.getDescription());
        newMovie.setMinute(movieDto.getMinute());
        newMovie.setRating(movieDto.getRating());

        return repo.save(newMovie);
    }

    // UPDATE: Aggiorna un film esistente usando il DTO (Modificato)
    public Optional<Movies> updateMovie(Long id, MoviesDTO updatedDto) {
        return repo.findById(id)
                .map(existing -> {
                    // Aggiorna l'entità esistente con i dati del DTO
                    existing.setName(updatedDto.getName());
                    existing.setDate(updatedDto.getDate());
                    existing.setTagline(updatedDto.getTagline());
                    existing.setDescription(updatedDto.getDescription());
                    existing.setMinute(updatedDto.getMinute());
                    existing.setRating(updatedDto.getRating());

                    return repo.save(existing);
                });
    }

    // Metodo aggiornato: da int a Long (Invariato)
    public boolean deleteMovie(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    // Aggiunto per il CsvDataLoader (Invariato)
    public List<Movies> findAll() {
        return repo.findAll();
    }

    // Il vecchio metodo createMovie(Movies m) è stato sostituito da createMovie(MoviesDTO m)
    // Similmente, updateMovie(Long id, Movies updated) è stato sostituito da updateMovie(Long id, MoviesDTO updatedDto)
}