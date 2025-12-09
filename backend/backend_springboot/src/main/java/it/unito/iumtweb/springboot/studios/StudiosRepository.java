package it.unito.iumtweb.springboot.studios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Studios} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface StudiosRepository extends JpaRepository<Studios, Long> {

    /**
     * Finds all studios for a specific movie ID.
     * @param movieId The Integer ID of the film.
     */
    List<Studios> findByMovieId(Integer movieId);

    /**
     * Searches for studios by name (partial match, case-insensitive).
     */
    Page<Studios> findByStudioNameContainingIgnoreCase(String studioName, Pageable pageable);
}