package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Studio management.
 * <p>
 * Handles data retrieval, pagination, and persistence.
 * </p>
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
     * Searches for studios by name with pagination.
     */
    public Page<Studios> searchStudios(String keyword, Pageable pageable) {
        return studiosRepository.findByIdStudioNameContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Retrieves studios by movie ID.
     */
    public List<Studios> getStudiosByMovieId(Long movieId) {
        return studiosRepository.findByIdMovieId(movieId);
    }

    /**
     * Retrieves a specific entry by composite key.
     */
    public Optional<Studios> getStudioByCompositeKey(Long movieId, String studioName) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(movieId, studioName);
        return studiosRepository.findById(pk);
    }

    /**
     * Creates a new studio association.
     */
    public Studios createStudio(StudiosDTO studioDto) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(studioDto.getMovieId(), studioDto.getStudioName());
        Studios newStudio = new Studios();
        newStudio.setId(pk);
        return studiosRepository.save(newStudio);
    }

    /**
     * Deletes a studio association.
     */
    public boolean deleteStudio(Long movieId, String studioName) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(movieId, studioName);
        if (studiosRepository.existsById(pk)) {
            studiosRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Update is technically just a check-exist in this case, but included for consistency
    public Optional<Studios> updateStudio(Long movieId, String studioName, StudiosDTO updatedDto) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(movieId, studioName);
        // Since there are no non-key fields, this is essentially a no-op or check
        return studiosRepository.findById(pk);
    }
}