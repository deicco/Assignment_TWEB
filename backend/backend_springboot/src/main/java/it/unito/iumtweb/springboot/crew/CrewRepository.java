package it.unito.iumtweb.springboot.crew;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Crew} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes methods supporting {@link Pageable} to handle the massive dataset (4.7M entries) efficiently.
 * </p>
 */
@Repository
public interface CrewRepository extends JpaRepository<Crew, CrewPrimaryKey> {

    /**
     * Finds all crew members for a specific movie.
     *
     * @param movieId The ID of the film.
     * @return A list of {@link Crew} members.
     */
    List<Crew> findByIdMovieId(Long movieId);

    /**
     * Finds all movies associated with a specific crew member name.
     *
     * @param crewName The name of the person.
     * @return A list of {@link Crew} entries.
     */
    List<Crew> findByIdCrewName(String crewName);

    /**
     * Searches for crew members by name (partial match) with pagination.
     *
     * @param crewName The search keyword.
     * @param pageable Pagination info.
     * @return A {@link Page} of matching crew members.
     */
    Page<Crew> findByIdCrewNameContainingIgnoreCase(String crewName, Pageable pageable);

    /**
     * Searches for crew members by role (e.g., "Director") with pagination.
     * Useful for journalists analyzing specific job categories.
     *
     * @param role     The role keyword.
     * @param pageable Pagination info.
     * @return A {@link Page} of matching entries.
     */
    Page<Crew> findByRoleContainingIgnoreCase(String role, Pageable pageable);
}