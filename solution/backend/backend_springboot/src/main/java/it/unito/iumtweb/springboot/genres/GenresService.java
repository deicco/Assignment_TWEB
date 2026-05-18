package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Genre management.
 */
@Service
public class GenresService {

    private final GenresRepository genresRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }
    /**
     * Retrieves all genre entries with pagination.
     */
    public Page<Genres> getAllGenres(Pageable pageable) {
        return genresRepository.findAll(pageable);
    }

    /**
     * Searches for genres by name.
     */
    public Page<Genres> searchGenres(String keyword, Pageable pageable) {
        return genresRepository.findByGenreContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves genres for a specific movie (Integer ID).
     */
    public List<Genres> getGenresByMovieId(Integer movieId) {
        return genresRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific genre by unique ID.
     */
    public Optional<Genres> getGenreById(Long id) {
        return genresRepository.findById(id);
    }

    /**
     * Creates a new genre association.
     */
    public Genres createGenre(GenresDTO dto) {
        Genres genre = new Genres(dto.getMovieId(), dto.getGenre());
        return genresRepository.save(genre);
    }

    /**
     * Updates an existing genre using unique ID.
     */
    public Optional<Genres> updateGenre(Long id, GenresDTO dto) {
        return genresRepository.findById(id)
                .map(existing -> {
                    existing.setGenre(dto.getGenre());
                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return genresRepository.save(existing);
                });
    }

    /**
     * Deletes a genre association by unique ID.
     */
    public boolean deleteGenre(Long id) {
        if (genresRepository.existsById(id)) {
            genresRepository.deleteById(id);
            return true;
        }
        return false;
    }
}