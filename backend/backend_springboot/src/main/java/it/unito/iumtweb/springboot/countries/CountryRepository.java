package it.unito.iumtweb.springboot.countries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for accessing {@link Country} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Finds all countries associated with a specific film ID.
     *
     * @param movieId The ID of the film (Integer).
     * @return A list of {@link Country} entities.
     */
    List<Country> findByMovieId(Integer movieId);

    /**
     * Searches for countries by name (partial match, case-insensitive) with pagination.
     *
     * @param country  The search string.
     * @param pageable The pagination info.
     * @return A {@link Page} of matching entities.
     */
    Page<Country> findByCountryContainingIgnoreCase(String country, Pageable pageable);
}