package it.unito.iumtweb.springboot.actors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link Actors} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and custom query methods
 * for the PostgreSQL database.
 * Includes methods supporting {@link Pageable} to handle the large dataset (5.7M entries) efficiently.
 * </p>
 */
@Repository
public interface ActorsRepository extends JpaRepository<Actors, ActorsPrimaryKey> {

    /**
     * Finds all actors associated with a specific film ID.
     *
     * @param movieId The ID of the film.
     * @return A list of {@link Actors} entities belonging to the specified film.
     */
    // Note: Usually casting is small enough to return a List, but could be paged if needed.
    java.util.List<Actors> findByIdId(Long movieId);

    /**
     * Finds all appearances of a specific actor by exact name.
     *
     * @param name The name of the actor (part of the composite key).
     * @return A list of {@link Actors} entities matching the given name.
     */
    java.util.List<Actors> findByIdName(String name);

    /**
     * Performs a search for actors whose name contains the specified string (case-insensitive).
     * <p>
     * This method is essential for the "Querying and Exploring" requirement.
     * </p>
     *
     * @param name     The substring to search for in the actor's name.
     * @param pageable The pagination information.
     * @return A {@link Page} of actors matching the criteria.
     */
    Page<Actors> findByIdNameContainingIgnoreCase(String name, Pageable pageable);
}