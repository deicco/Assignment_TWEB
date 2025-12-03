package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Crew resources.
 * <p>
 * Exposes endpoints to query film crew data (Directors, Writers, etc.).
 * Supports pagination and search to handle the large dataset (4.7M entries).
 * </p>
 */
@RestController
@RequestMapping("/crew")
// Enables Cross-Origin requests from the Express Server
@CrossOrigin(origins = "http://localhost:3000")
public class CrewController {

    @Autowired
    private CrewService crewService;

    /**
     * GET /crew
     * Retrieves a paginated list of crew members. Can filter by name or role.
     * <p>
     * Example: /crew?role=Director&page=0&size=10
     * </p>
     *
     * @param page Page number (default 0).
     * @param size Items per page (default 20).
     * @param name Optional search by name.
     * @param role Optional search by role.
     * @return A {@link Page} of {@link Crew}.
     */
    @GetMapping
    public ResponseEntity<Page<Crew>> getAllCrew(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String role) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(crewService.searchCrew(name, role, pageable));
    }

    /**
     * GET /crew/movie/{movieId}
     * Retrieves all crew members for a specific movie.
     *
     * @param movieId The movie ID.
     * @return List of crew members.
     */
    @GetMapping("/movie/{movieId}")
    public List<Crew> getCrewByMovieId(@PathVariable Long movieId) {
        return crewService.getCrewByMovieId(movieId);
    }

    /**
     * GET /crew/{movieId}/{crewName}
     * Retrieves a specific member by composite key.
     */
    @GetMapping("/{movieId}/{crewName}")
    public ResponseEntity<Crew> getCrewByCompositeId(@PathVariable Long movieId, @PathVariable String crewName) {
        Optional<Crew> crew = crewService.getCrewByCompositeId(movieId, crewName);
        return crew.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /crew
     * Creates a new crew member.
     */
    @PostMapping
    public Crew createCrew(@RequestBody CrewDTO crewDto) {
        return crewService.createCrew(crewDto);
    }

    /**
     * PUT /crew/{movieId}/{crewName}
     * Updates an existing member's role.
     */
    @PutMapping("/{movieId}/{crewName}")
    public ResponseEntity<Crew> updateCrew(@PathVariable Long movieId, @PathVariable String crewName, @RequestBody CrewDTO updatedCrewDto) {
        Crew result = crewService.updateCrew(movieId, crewName, updatedCrewDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /crew/{movieId}/{crewName}
     * Deletes a crew member.
     */
    @DeleteMapping("/{movieId}/{crewName}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long movieId, @PathVariable String crewName) {
        boolean deleted = crewService.deleteCrew(movieId, crewName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}