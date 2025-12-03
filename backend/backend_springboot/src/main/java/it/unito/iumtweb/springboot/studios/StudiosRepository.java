package it.unito.iumtweb.springboot.studios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Studios} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes methods supporting {@link Pageable} to handle large datasets.
 * </p>
 */
@Repository
public interface StudiosRepository extends JpaRepository<Studios, StudiosPrimaryKey> {

    /**
     * Finds all studios for a specific movie.
     */
    List<Studios> findByIdMovieId(Long movieId);

    /**
     * Searches for studios by name (partial match, case-insensitive) with pagination.
     * <p>
     * Useful for exploring movies by studio (e.g., searching for "Disney").
     * </p>
     *
     * @param studioName The search keyword.
     * @param pageable   Pagination info.
     * @return A {@link Page} of matching entities.
     */
    Page<Studios> findByIdStudioNameContainingIgnoreCase(String studioName, Pageable pageable);
}