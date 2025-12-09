package it.unito.iumtweb.springboot.releases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Releases} data.
 * <p>
 * Extends {@link JpaRepository} with Long as the ID type.
 * </p>
 */
@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {

    /**
     * Finds all releases for a specific movie ID.
     * @param movieId The Integer ID of the film.
     */
    List<Releases> findByMovieId(Integer movieId);

    /**
     * Finds releases by country with pagination.
     */
    Page<Releases> findByCountry(String country, Pageable pageable);
}