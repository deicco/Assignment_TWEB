package it.unito.iumtweb.springboot.crew;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Crew} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {

    /**
     * Finds all crew members for a specific movie.
     * @param movieId The Integer ID of the film.
     */
    List<Crew> findByMovieId(Integer movieId);

    /**
     * Searches for crew members by name (partial match).
     */
    Page<Crew> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Searches for crew members by role (e.g., "Director").
     */
    Page<Crew> findByRoleContainingIgnoreCase(String role, Pageable pageable);
}