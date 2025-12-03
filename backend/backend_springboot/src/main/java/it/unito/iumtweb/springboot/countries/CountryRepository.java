package it.unito.iumtweb.springboot.countries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for accessing {@link Country} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and custom query methods.
 * Includes methods supporting {@link Pageable} to handle the large dataset (693k entries) efficiently.
 * </p>
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, CountryPrimaryKey> {

    /**
     * Finds all countries associated with a specific film ID.
     *
     * @param movieId The ID of the film (inside the composite PK).
     * @return A list of {@link Country} entities.
     */
    List<Country> findByIdMovieId(Long movieId);

    /**
     * Finds all films associated with a specific country name.
     *
     * @param country The name of the country.
     * @return A list of {@link Country} entities.
     */
    List<Country> findByIdCountry(String country);

    /**
     * Searches for countries by name (partial match, case-insensitive) with pagination.
     * <p>
     * Enables users to explore films by country (e.g., searching for "Italy" or "USA").
     * </p>
     *
     * @param country  The search string.
     * @param pageable The pagination info.
     * @return A {@link Page} of matching {@link Country} entities.
     */
    Page<Country> findByIdCountryContainingIgnoreCase(String country, Pageable pageable);
}