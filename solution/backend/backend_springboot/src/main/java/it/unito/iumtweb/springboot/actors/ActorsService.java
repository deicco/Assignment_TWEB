package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Actor management.
 * <p>
 * Handles CRUD operations and business logic using the surrogate Key (Long ID).
 * </p>
 */
@Service
public class ActorsService {

    private final ActorsRepository actorsRepository;

    @Autowired
    public ActorsService(ActorsRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }
    /**
     * Retrieves all actors (paginated).
     */
    public Page<Actors> getAllActors(Pageable pageable) {
        return actorsRepository.findAll(pageable);
    }

    /**
     * Searches actors by name (paginated).
     */
    public Page<Actors> searchActorsByName(String name, Pageable pageable) {
        return actorsRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    /**
     * Retrieves all actors for a specific movie.
     * @param movieId The Integer ID of the movie.
     */
    public List<Actors> getActorsByMovieId(Integer movieId) {
        return actorsRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a single actor by their unique ID.
     */
    public Optional<Actors> getActorById(Long id) {
        return actorsRepository.findById(id);
    }

    /**
     * Creates a new actor.
     */
    public Actors createActor(ActorsDTO dto) {
        // Create new entity from DTO
        Actors actor = new Actors(dto.getMovieId(), dto.getName(), dto.getRole());
        return actorsRepository.save(actor);
    }

    /**
     * Updates an existing actor using its unique ID.
     */
    public Optional<Actors> updateActor(Long id, ActorsDTO dto) {
        return actorsRepository.findById(id)
                .map(existing -> {
                    // Update editable fields
                    existing.setName(dto.getName());
                    existing.setRole(dto.getRole());

                    // Update movieId if changed (keeping type consistency: Integer)
                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return actorsRepository.save(existing);
                });
    }

    /**
     * Deletes an actor by unique ID.
     */
    public boolean deleteActor(Long id) {
        if (!actorsRepository.existsById(id)) {
            return false;
        }
        actorsRepository.deleteById(id);
        return true;
    }
}