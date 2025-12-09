package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Studio management.
 */
@Service
public class StudiosService {

    @Autowired
    private StudiosRepository studiosRepository;

    /**
     * Retrieves all studio entries with pagination.
     */
    public Page<Studios> getAllStudios(Pageable pageable) {
        return studiosRepository.findAll(pageable);
    }

    /**
     * Searches for studios by name.
     */
    public Page<Studios> searchStudios(String keyword, Pageable pageable) {
        return studiosRepository.findByStudioNameContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves studios by movie ID (Integer).
     */
    public List<Studios> getStudiosByMovieId(Integer movieId) {
        return studiosRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific entry by unique ID.
     */
    public Optional<Studios> getStudioById(Long id) {
        return studiosRepository.findById(id);
    }

    /**
     * Creates a new studio association.
     */
    public Studios createStudio(StudiosDTO studioDto) {
        Studios newStudio = new Studios(studioDto.getMovieId(), studioDto.getStudioName());
        return studiosRepository.save(newStudio);
    }

    /**
     * Updates an existing studio entry by unique ID.
     */
    public Optional<Studios> updateStudio(Long id, StudiosDTO updatedDto) {
        return studiosRepository.findById(id)
                .map(existing -> {
                    existing.setStudioName(updatedDto.getStudioName());
                    if (updatedDto.getMovieId() != null) {
                        existing.setMovieId(updatedDto.getMovieId());
                    }
                    return studiosRepository.save(existing);
                });
    }

    /**
     * Deletes a studio association by unique ID.
     */
    public boolean deleteStudio(Long id) {
        if (studiosRepository.existsById(id)) {
            studiosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}