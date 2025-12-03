package it.unito.iumtweb.springboot.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repository interface for accessing {@link Movies} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes custom query methods for filtering data by name, rating, date, and duration.
 * Supports {@link Pageable} to efficiently handle the large dataset (approx. 940k records).
 * </p>
 */
@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    /**
     * Finds movies whose name contains the given string (case-insensitive).
     * @param name The search string.
     * @param pageable Pagination info.
     * @return A page of matching movies.
     */
    Page<Movies> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds movies with a rating within a specific range.
     * @param min Minimum rating.
     * @param max Maximum rating.
     * @param pageable Pagination info.
     * @return A page of matching movies.
     */
    Page<Movies> findByRatingBetween(float min, float max, Pageable pageable);

    /**
     * Finds movies released within a specific date range.
     * @param start Start date.
     * @param end End date.
     * @param pageable Pagination info.
     * @return A page of matching movies.
     */
    Page<Movies> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * Finds movies with a duration greater than the specified minutes.
     * Useful for analyzing long-form content.
     * @param minute Minimum duration in minutes.
     * @param pageable Pagination info.
     * @return A page of matching movies.
     */
    Page<Movies> findByMinuteGreaterThan(int minute, Pageable pageable);
}