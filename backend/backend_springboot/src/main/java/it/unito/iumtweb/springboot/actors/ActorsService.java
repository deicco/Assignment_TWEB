package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating the business logic for Actor management.
 * <p>
 * Acts as an intermediary between the {@link ActorsController} and the {@link ActorsRepository}.
 * It handles data transformation (DTO to Entity) and ensures efficient data retrieval using pagination.
 * </p>
 */
@Service
public class ActorsService {

    @Autowired
    private ActorsRepository actorsRepository;

    /**
     * Retrieves all actor entries from the database with pagination.
     * <p>
     * Using pagination is mandatory due to the large size of the dataset (5.7 million records).
     * </p>
     *
     * @param pageable The pagination information (page number and size).
     * @return A {@link Page} of {@link Actors} entities.
     */
    public Page<Actors> getAllActors(Pageable pageable) {
        return actorsRepository.findAll(pageable);
    }

    /**
     * Retrieves all actors for a specific movie.
     *
     * @param movieId The unique identifier of the movie.
     * @return A list of actors in the specified movie.
     */
    public List<Actors> getActorsByMovieId(Long movieId) {
        return actorsRepository.findByIdId(movieId);
    }

    /**
     * Retrieves a specific actor entry using the composite key.
     *
     * @param movieId   The ID of the movie.
     * @param actorName The name of the actor.
     * @return An {@link Optional} containing the actor if found, otherwise empty.
     */
    public Optional<Actors> getActorByCompositeId(Long movieId, String actorName) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(movieId, actorName);
        return actorsRepository.findById(pk);
    }

    /**
     * Searches for actors by name (partial match) with pagination.
     *
     * @param name     The name (or part of it) to search for.
     * @param pageable The pagination info.
     * @return A page of matching actors.
     */
    public Page<Actors> searchActorsByName(String name, Pageable pageable) {
        return actorsRepository.findByIdNameContainingIgnoreCase(name, pageable);
    }

    /**
     * Creates and persists a new actor association based on the provided DTO.
     *
     * @param actorDto The data transfer object containing actor details.
     * @return The persisted {@link Actors} entity.
     */
    public Actors createActor(ActorsDTO actorDto) {
        // BUG FIX: Correctly mapping movieId from DTO to ID in PrimaryKey
        ActorsPrimaryKey pk = new ActorsPrimaryKey(actorDto.getMovieId(), actorDto.getName());

        Actors newActor = new Actors();
        newActor.setId(pk);
        newActor.setRole(actorDto.getRole());
        return actorsRepository.save(newActor);
    }

    /**
     * Updates an existing actor's role.
     *
     * @param movieId         The ID of the movie.
     * @param actorName       The name of the actor.
     * @param updatedActorDto The DTO containing the new role information.
     * @return The updated {@link Actors} entity, or {@code null} if the actor does not exist.
     */
    public Actors updateActor(Long movieId, String actorName, ActorsDTO updatedActorDto) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(movieId, actorName);
        return actorsRepository.findById(pk)
                .map(existingActor -> {
                    // Updates only the non-key field 'role'
                    existingActor.setRole(updatedActorDto.getRole());
                    return actorsRepository.save(existingActor);
                })
                .orElse(null);
    }

    /**
     * Deletes an actor association from the database.
     *
     * @param movieId   The ID of the movie.
     * @param actorName The name of the actor.
     * @return {@code true} if the deletion was successful; {@code false} if the entity was not found.
     */
    public boolean deleteActor(Long movieId, String actorName) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(movieId, actorName);
        if (actorsRepository.existsById(pk)) {
            actorsRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}