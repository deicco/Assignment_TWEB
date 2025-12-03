package it.unito.iumtweb.springboot.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Service class encapsulating business logic for Movie management.
 * <p>
 * Handles filtering logic, data transformation, and transaction management.
 * </p>
 */
@Service
public class MoviesService {

    @Autowired
    private MoviesRepository repo;

    /**
     * Retrieves a paginated list of movies based on optional filters.
     * <p>
     * Filters are applied hierarchically:
     * 1. Name search
     * 2. Rating range
     * 3. Date range
     * 4. Duration (minute) filter
     * If no filters are provided, returns all movies.
     * </p>
     *
     * @param name Name keyword.
     * @param minRating Minimum rating.
     * @param maxRating Maximum rating.
     * @param startDate Start date.
     * @param endDate End date.
     * @param minMinute Minimum duration.
     * @param pageable Pagination info.
     * @return A {@link Page} of {@link Movies}.
     */
    public Page<Movies> getMovies(
            String name,
            Float minRating,
            Float maxRating,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer minMinute,
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
        if (minMinute != null) {
            return repo.findByMinuteGreaterThan(minMinute, pageable);
        }
        return repo.findAll(pageable);
    }

    /**
     * Retrieves a single movie by its ID.
     */
    public Optional<Movies> getMovieById(Long id) {
        return repo.findById(id);
    }

    /**
     * Creates a new movie entry from a DTO.
     */
    public Movies createMovie(MoviesDTO movieDto) {
        Movies newMovie = new Movies();
        newMovie.setName(movieDto.getName());
        newMovie.setDate(movieDto.getDate());
        newMovie.setTagline(movieDto.getTagline());
        newMovie.setDescription(movieDto.getDescription());
        newMovie.setMinute(movieDto.getMinute());
        newMovie.setRating(movieDto.getRating());
        return repo.save(newMovie);
    }

    /**
     * Updates an existing movie entry.
     */
    public Optional<Movies> updateMovie(Long id, MoviesDTO updatedDto) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(updatedDto.getName());
                    existing.setDate(updatedDto.getDate());
                    existing.setTagline(updatedDto.getTagline());
                    existing.setDescription(updatedDto.getDescription());
                    existing.setMinute(updatedDto.getMinute());
                    existing.setRating(updatedDto.getRating());
                    return repo.save(existing);
                });
    }

    /**
     * Deletes a movie by ID.
     */
    public boolean deleteMovie(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}