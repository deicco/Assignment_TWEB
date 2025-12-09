package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Release management.
 */
@Service
public class ReleasesService {

    @Autowired
    private ReleasesRepository releasesRepository;

    /**
     * Retrieves all releases with pagination.
     */
    public Page<Releases> getAllReleases(Pageable pageable) {
        return releasesRepository.findAll(pageable);
    }

    /**
     * Retrieves releases by country.
     */
    public Page<Releases> getReleasesByCountry(String country, Pageable pageable) {
        return releasesRepository.findByCountry(country, pageable);
    }

    /**
     * Retrieves releases for a specific movie (Integer ID).
     */
    public List<Releases> getReleasesByMovieId(Integer movieId) {
        return releasesRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific release by unique ID.
     */
    public Optional<Releases> getReleaseById(Long id) {
        return releasesRepository.findById(id);
    }

    /**
     * Creates a new release entry.
     */
    public Releases createRelease(ReleasesDTO dto) {
        Releases newRelease = new Releases(
                dto.getMovieId(),
                dto.getCountry(),
                dto.getDate(),
                dto.getType(),
                dto.getRating()
        );
        return releasesRepository.save(newRelease);
    }

    /**
     * Updates an existing release entry by unique ID.
     */
    public Optional<Releases> updateRelease(Long id, ReleasesDTO dto) {
        return releasesRepository.findById(id)
                .map(existing -> {
                    existing.setCountry(dto.getCountry());
                    existing.setDate(dto.getDate());
                    existing.setType(dto.getType());
                    existing.setRating(dto.getRating());
                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return releasesRepository.save(existing);
                });
    }

    /**
     * Deletes a release entry by unique ID.
     */
    public boolean deleteRelease(Long id) {
        if (releasesRepository.existsById(id)) {
            releasesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}