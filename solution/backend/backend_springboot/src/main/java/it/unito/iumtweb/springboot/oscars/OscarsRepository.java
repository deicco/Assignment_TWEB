package it.unito.iumtweb.springboot.oscars;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Oscars} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface OscarsRepository extends JpaRepository<Oscars, Long> {

    /**
     * Finds all awards/nominations for a specific film ID.
     */
    List<Oscars> findByMovieId(Integer movieId);

    /**
     * Finds awards by film name (partial match).
     */
    Page<Oscars> findByFilmContainingIgnoreCase(String film, Pageable pageable);

    /**
     * Finds awards by nominee name (partial match).
     */
    Page<Oscars> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds only the winners.
     */
    Page<Oscars> findByWinnerTrue(Pageable pageable);

    /**
     * Finds winners in a specific category.
     */
    Page<Oscars> findByCategoryAndWinnerTrue(String category, Pageable pageable);
}