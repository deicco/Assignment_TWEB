package it.unito.iumtweb.springboot.actors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for accessing {@link Actors} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * Provides custom queries for filtering by Movie ID (Integer) and Name.
 * </p>
 */
@Repository
public interface ActorsRepository extends JpaRepository<Actors, Long> {

    /**
     * Finds all actors associated with a specific film ID.
     *
     * @param movieId The ID of the film (Integer).
     * @return A list of actors in that movie.
     */
    @Query("SELECT a FROM Actors a WHERE a.movieId = :movieId")
    List<Actors> findByMovieId(Integer movieId);

    /**
     * Performs a search for actors whose name contains the specified string (case-insensitive).
     *
     * @param name     The substring to search for.
     * @param pageable Pagination info.
     * @return A Page of matching actors.
     */
    Page<Actors> findByNameContainingIgnoreCase(String name, Pageable pageable);
}