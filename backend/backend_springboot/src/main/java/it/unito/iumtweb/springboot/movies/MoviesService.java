package it.unito.iumtweb.springboot.movies;

import it.unito.iumtweb.springboot.actors.Actors;
import it.unito.iumtweb.springboot.actors.ActorsRepository;
import it.unito.iumtweb.springboot.poster.Poster;
import it.unito.iumtweb.springboot.poster.PosterRepository;
import it.unito.iumtweb.springboot.languages.Languages;
import it.unito.iumtweb.springboot.languages.LanguagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Movie management.
 * <p>
 * Handles filtering logic, data transformation, and transaction management.
 * </p>
 */
@Service
public class MoviesService {

    @Autowired private MoviesRepository repo;
    @Autowired private PosterRepository posterRepo;
    @Autowired private ActorsRepository actorsRepo;
    @Autowired private LanguagesRepository languageRepo;

    /**
     * Retrieves a paginated list of movies based on optional filters.
     */
    public Optional<MovieDetailDTO> getMovieDetailById(Long id) {
        return repo.findById(id).map(movie -> {
            Integer movieIdInt = id.intValue();

            List<Poster> posters = posterRepo.findByMovieId(movieIdInt);
            List<Actors> cast = actorsRepo.findByMovieId(movieIdInt);
            List<Languages> languages = languageRepo.findByMovieId(movieIdInt);

           return new MovieDetailDTO(movie, posters, cast, languages);
        });
    }

    public Page<Movies> getMovies(
            String name,
            Float minRating,
            Float maxRating,
            Integer startYear,
            Integer endYear,
            Integer minMinute,
            Pageable pageable
    ) {
        if (name != null && !name.isBlank()) {
            return repo.findByNameContainingIgnoreCase(name, pageable);
        }
        if (minRating != null && maxRating != null) {
            return repo.findByRatingBetween(minRating, maxRating, pageable);
        }
        if (startYear != null && endYear != null) {
            return repo.findByDateBetween(startYear, endYear, pageable);
        }
        if (minMinute != null) {
            return repo.findByMinuteGreaterThan(minMinute, pageable);
        }
        return repo.findAll(pageable);
    }

    public Optional<Movies> getMovieById(Long id) {
        return repo.findById(id);
    }

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

    public boolean deleteMovie(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}