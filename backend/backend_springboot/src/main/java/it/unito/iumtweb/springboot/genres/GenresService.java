package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class encapsulating business logic for Genre management.
 * <p>
 * Handles data retrieval, pagination, and persistence for genre-movie associations.
 * </p>
 */
@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    /**
     * Retrieves all genre entries with pagination.
     *
     * @param pageable Pagination information.
     * @return A {@link Page} of {@link Genres}.
     */
    public Page<Genres> getAllGenres(Pageable pageable) {
        return genresRepository.findAll(pageable);
    }

    /**
     * Searches for genres by name with pagination.
     *
     * @param keyword  The genre name keyword.
     * @param pageable Pagination information.
     * @return A {@link Page} of matching entities.
     */
    public Page<Genres> searchGenres(String keyword, Pageable pageable) {
        return genresRepository.findByIdGenreContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves genres for a specific movie.
     * @param movieId The movie ID.
     * @return List of genres.
     */
    public List<Genres> getGenresByMovieId(Long movieId) {
        return genresRepository.findByIdMovieId(movieId);
    }

    /**
     * Creates a new genre association.
     * @param dto The data transfer object.
     * @return The saved entity.
     */
    public Genres createGenre(GenresDTO dto) {
        // Updated to use movieId for consistency
        GenresPrimaryKey pk = new GenresPrimaryKey(dto.getMovieId(), dto.getGenre());
        Genres genre = new Genres();
        genre.setId(pk);
        return genresRepository.save(genre);
    }

    /**
     * Deletes a genre association.
     * @param movieId The movie ID.
     * @param genre The genre name.
     * @return True if deleted, false if not found.
     */
    public boolean deleteGenre(Long movieId, String genre) {
        GenresPrimaryKey pk = new GenresPrimaryKey(movieId, genre);
        if (genresRepository.existsById(pk)) {
            genresRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}