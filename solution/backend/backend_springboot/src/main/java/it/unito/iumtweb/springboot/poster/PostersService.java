package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Poster management.
 */
@Service
public class PostersService {

    private final PosterRepository posterRepository;

    @Autowired
    public PostersService(PosterRepository posterRepository) {
        this.posterRepository = posterRepository;
    }

    /**
     * Retrieves all posters with pagination.
     */
    public Page<Poster> getAllPosters(Pageable pageable) {
        return posterRepository.findAll(pageable);
    }

    /**
     * Retrieves a poster by Movie ID (Integer).
     */
    public List<Poster> getPosterByMovieId(Integer movieId) {
        return posterRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a poster by its unique ID (Long).
     */
    public Optional<Poster> getPosterById(Long id) {
        return posterRepository.findById(id);
    }

    /**
     * Creates a new poster entry.
     */
    public Poster createPoster(PosterDTO posterDto) {
        Poster newPoster = new Poster(posterDto.getMovieId(), posterDto.getLink());
        return posterRepository.save(newPoster);
    }

    /**
     * Updates an existing poster link by unique ID.
     */
    public Optional<Poster> updatePoster(Long id, PosterDTO updatedPosterDto) {
        return posterRepository.findById(id)
                .map(existingPoster -> {
                    existingPoster.setLink(updatedPosterDto.getLink());
                    if (updatedPosterDto.getMovieId() != null) {
                        existingPoster.setMovieId(updatedPosterDto.getMovieId());
                    }
                    return posterRepository.save(existingPoster);
                });
    }

    /**
     * Deletes a poster by unique ID.
     */
    public boolean deletePoster(Long id) {
        if (posterRepository.existsById(id)) {
            posterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Poster> getPostersByMovieId(Integer movieId) {
        return List.of();
    }
}