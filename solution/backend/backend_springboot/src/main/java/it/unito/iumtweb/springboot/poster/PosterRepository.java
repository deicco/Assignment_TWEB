package it.unito.iumtweb.springboot.poster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing {@link Poster} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {

    /**
     * Finds the poster associated with a specific film ID.
     * @param movieId The Integer ID of the film.
     * @return An Optional containing the Poster if found.
     */
    List<Poster> findByMovieId(Integer movieId);}