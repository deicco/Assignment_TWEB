package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class encapsulating business logic for Poster management.
 * <p>
 * Handles retrieval and persistence of movie poster links.
 * </p>
 */
@Service
public class PostersService {

    @Autowired
    private PosterRepository posterRepository;

    /**
     * Retrieves all posters with pagination.
     * <p>
     * <b>Mandatory:</b> Prevents OutOfMemory errors on large dataset (940k rows).
     * </p>
     *
     * @param pageable Pagination info.
     * @return A {@link Page} of {@link Poster}.
     */
    public Page<Poster> getAllPosters(Pageable pageable) {
        return posterRepository.findAll(pageable);
    }

    /**
     * Retrieves a poster by Movie ID.
     */
    public Optional<Poster> getPosterById(Long id) {
        return posterRepository.findById(id);
    }

    /**
     * Creates a new poster entry.
     *
     * @param movieId The ID of the film (used as PK).
     * @param posterDto The DTO containing the link.
     * @return The saved entity.
     */
    public Poster createPoster(Long movieId, PosterDTO posterDto) {
        Poster newPoster = new Poster();
        newPoster.setId(movieId);
        newPoster.setLink(posterDto.getLink());
        return posterRepository.save(newPoster);
    }

    /**
     * Updates an existing poster link.
     */
    public Poster updatePoster(Long id, PosterDTO updatedPosterDto) {
        return posterRepository.findById(id)
                .map(existingPoster -> {
                    existingPoster.setLink(updatedPosterDto.getLink());
                    return posterRepository.save(existingPoster);
                })
                .orElse(null);
    }

    /**
     * Deletes a poster.
     */
    public boolean deletePoster(Long id) {
        if (posterRepository.existsById(id)) {
            posterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}