package it.unito.iumtweb.springboot.oscars;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link Oscars} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes specialized queries for analyzing winners.
 * </p>
 */
@Repository
public interface OscarsRepository extends JpaRepository<Oscars, OscarsPrimaryKey> {

    /**
     * Finds all awards/nominations for a specific film (partial match).
     */
    Page<Oscars> findByIdFilmContainingIgnoreCase(String film, Pageable pageable);

    /**
     * Finds all awards/nominations for a specific person (partial match).
     */
    Page<Oscars> findByIdNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds only the winners.
     * Useful for creating "Hall of Fame" lists.
     */
    Page<Oscars> findByWinnerTrue(Pageable pageable);

    /**
     * Finds winners in a specific category.
     */
    Page<Oscars> findByCategoryAndWinnerTrue(String category, Pageable pageable);
}