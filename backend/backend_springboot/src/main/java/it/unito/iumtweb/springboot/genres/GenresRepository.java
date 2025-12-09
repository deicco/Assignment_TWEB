package it.unito.iumtweb.springboot.genres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Genres} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {

    /**
     * Finds all genres associated with a specific film ID.
     * @param movieId The Integer ID of the film.
     */
    List<Genres> findByMovieId(Integer movieId);

    /**
     * Searches for genres by name (partial match, case-insensitive).
     */
    Page<Genres> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
}