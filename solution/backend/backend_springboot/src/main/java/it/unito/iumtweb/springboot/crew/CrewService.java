package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Crew management.
 */
@Service
public class CrewService {

    @Autowired
    private CrewRepository crewRepository;

    /**
     * Retrieves all crew entries with pagination.
     */
    public Page<Crew> getAllCrew(Pageable pageable) {
        return crewRepository.findAll(pageable);
    }

    /**
     * Searches crew members by name or role.
     */
    public Page<Crew> searchCrew(String name, String role, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return crewRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (role != null && !role.isEmpty()) {
            return crewRepository.findByRoleContainingIgnoreCase(role, pageable);
        }
        return crewRepository.findAll(pageable);
    }

    /**
     * Retrieves crew by Movie ID (Integer).
     */
    public List<Crew> getCrewByMovieId(Integer movieId) {
        return crewRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific member by unique ID.
     */
    public Optional<Crew> getCrewById(Long id) {
        return crewRepository.findById(id);
    }

    /**
     * Creates a new crew member association.
     */
    public Crew createCrew(CrewDTO dto) {
        Crew newCrew = new Crew(dto.getMovieId(), dto.getName(), dto.getRole());
        return crewRepository.save(newCrew);
    }

    /**
     * Updates an existing crew member using unique ID.
     */
    public Optional<Crew> updateCrew(Long id, CrewDTO dto) {
        return crewRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setRole(dto.getRole());

                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return crewRepository.save(existing);
                });
    }

    /**
     * Deletes a crew member by unique ID.
     */
    public boolean deleteCrew(Long id) {
        if (crewRepository.existsById(id)) {
            crewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}