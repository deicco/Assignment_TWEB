package it.unito.iumtweb.springboot.genres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Genres} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes methods supporting {@link Pageable} to handle the large volume of data efficiently.
 * </p>
 */
@Repository
public interface GenresRepository extends JpaRepository<Genres, GenresPrimaryKey> {

    /**
     * Finds all genres associated with a specific film ID.
     *
     * @param movieId The ID of the film (part of the PK).
     * @return A list of {@link Genres}.
     */
    List<Genres> findByIdMovieId(Long movieId);

    /**
     * Finds all movies associated with a specific genre (exact match).
     *
     * @param genre The genre name.
     * @return A list of {@link Genres}.
     */
    List<Genres> findByIdGenre(String genre);

    /**
     * Searches for genres by name (partial match, case-insensitive) with pagination.
     * <p>
     * Useful for exploring movies by genre (e.g., searching for "Sci-Fi").
     * </p>
     *
     * @param genre    The search keyword.
     * @param pageable The pagination info.
     * @return A {@link Page} of matching entities.
     */
    Page<Genres> findByIdGenreContainingIgnoreCase(String genre, Pageable pageable);
}