package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Release management.
 * <p>
 * Handles data retrieval and pagination for the largest dataset in the system.
 * </p>
 */
@Service
public class ReleasesService {

    @Autowired
    private ReleasesRepository releasesRepository;

    /**
     * Retrieves all releases with pagination.
     * <p>
     * <b>Mandatory:</b> Without pagination, fetching 13M records causes an OutOfMemoryError.
     * </p>
     *
     * @param pageable Pagination info.
     * @return A {@link Page} of {@link Releases}.
     */
    public Page<Releases> getAllReleases(Pageable pageable) {
        return releasesRepository.findAll(pageable);
    }

    /**
     * Retrieves releases by country with pagination.
     */
    public Page<Releases> getReleasesByCountry(String country, Pageable pageable) {
        return releasesRepository.findByIdCountry(country, pageable);
    }

    /**
     * Retrieves releases for a specific movie.
     */
    public List<Releases> getReleasesByMovieId(Long movieId) {
        return releasesRepository.findByIdMovieId(movieId);
    }

    /**
     * Retrieves a specific release by composite key.
     */
    public Optional<Releases> getReleaseByCompositeKey(Long movieId, String country) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(movieId, country);
        return releasesRepository.findById(pk);
    }

    /**
     * Creates a new release entry.
     */
    public Releases createRelease(ReleasesDTO releaseDto) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(releaseDto.getMovieId(), releaseDto.getCountry());
        Releases newRelease = new Releases();
        newRelease.setId(pk);
        newRelease.setDate(releaseDto.getDate());
        newRelease.setType(releaseDto.getType());
        newRelease.setRating(releaseDto.getRating());
        return releasesRepository.save(newRelease);
    }

    /**
     * Updates an existing release entry.
     */
    public Optional<Releases> updateRelease(Long movieId, String country, ReleasesDTO updatedDto) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(movieId, country);
        return releasesRepository.findById(pk)
                .map(existingRelease -> {
                    existingRelease.setDate(updatedDto.getDate());
                    existingRelease.setType(updatedDto.getType());
                    existingRelease.setRating(updatedDto.getRating());
                    return releasesRepository.save(existingRelease);
                });
    }

    /**
     * Deletes a release entry.
     */
    public boolean deleteRelease(Long movieId, String country) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(movieId, country);
        if (releasesRepository.existsById(pk)) {
            releasesRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}