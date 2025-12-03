package it.unito.iumtweb.springboot.releases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Releases} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes pagination support to handle the massive dataset (13M entries).
 * </p>
 */
@Repository
public interface ReleasesRepository extends JpaRepository<Releases, ReleasesPrimaryKey> {

    /**
     * Finds all releases for a specific movie.
     * @param movieId The film ID.
     * @return List of releases.
     */
    List<Releases> findByIdMovieId(Long movieId);

    /**
     * Finds releases by country with pagination.
     * @param country The country name.
     * @param pageable Pagination info.
     * @return A {@link Page} of releases.
     */
    Page<Releases> findByIdCountry(String country, Pageable pageable);
}