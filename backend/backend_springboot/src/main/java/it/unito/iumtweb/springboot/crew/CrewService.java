package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Crew management.
 * <p>
 * Handles data retrieval, pagination, and persistence for crew members.
 * </p>
 */
@Service
public class CrewService {

    @Autowired
    private CrewRepository crewRepository;

    /**
     * Retrieves all crew entries with pagination.
     * <p>
     * <b>Mandatory:</b> Without pagination, fetching 4.7M records would crash the server.
     * </p>
     *
     * @param pageable Pagination information.
     * @return A {@link Page} of {@link Crew}.
     */
    public Page<Crew> getAllCrew(Pageable pageable) {
        return crewRepository.findAll(pageable);
    }

    /**
     * Searches crew members by name or role.
     *
     * @param name     Optional name filter.
     * @param role     Optional role filter.
     * @param pageable Pagination info.
     * @return A {@link Page} of matching results.
     */
    public Page<Crew> searchCrew(String name, String role, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return crewRepository.findByIdCrewNameContainingIgnoreCase(name, pageable);
        } else if (role != null && !role.isEmpty()) {
            return crewRepository.findByRoleContainingIgnoreCase(role, pageable);
        }
        return crewRepository.findAll(pageable);
    }

    /**
     * Retrieves crew by Movie ID.
     */
    public List<Crew> getCrewByMovieId(Long movieId) {
        return crewRepository.findByIdMovieId(movieId);
    }

    /**
     * Retrieves a specific member by composite ID.
     */
    public Optional<Crew> getCrewByCompositeId(Long movieId, String crewName) {
        CrewPrimaryKey pk = new CrewPrimaryKey(movieId, crewName);
        return crewRepository.findById(pk);
    }

    /**
     * Creates a new crew member association.
     */
    public Crew createCrew(CrewDTO crewDto) {
        CrewPrimaryKey pk = new CrewPrimaryKey(crewDto.getMovieId(), crewDto.getCrewName());
        Crew newCrew = new Crew(pk, crewDto.getRole());
        return crewRepository.save(newCrew);
    }

    /**
     * Updates an existing crew member's role.
     */
    public Crew updateCrew(Long movieId, String crewName, CrewDTO updatedCrewDto) {
        CrewPrimaryKey pk = new CrewPrimaryKey(movieId, crewName);
        return crewRepository.findById(pk)
                .map(existingCrew -> {
                    existingCrew.setRole(updatedCrewDto.getRole());
                    return crewRepository.save(existingCrew);
                })
                .orElse(null);
    }

    /**
     * Deletes a crew member.
     */
    public boolean deleteCrew(Long movieId, String crewName) {
        CrewPrimaryKey pk = new CrewPrimaryKey(movieId, crewName);
        if (crewRepository.existsById(pk)) {
            crewRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}