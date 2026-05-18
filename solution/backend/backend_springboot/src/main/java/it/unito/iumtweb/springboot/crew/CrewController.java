package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Crew resources.
 */
@RestController
@RequestMapping("/crew")
@CrossOrigin(origins = "http://localhost:3000")
public class CrewController {

    private final CrewService crewService;

    @Autowired
    public CrewController(CrewService crewService) {
        this.crewService = crewService;
    }

    /**
     * GET /crew
     * List all crew with filters.
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
     * Get crew for a movie.
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Crew>> getCrewByMovieId(@PathVariable Integer movieId) {
        List<Crew> crew = crewService.getCrewByMovieId(movieId);
        return ResponseEntity.ok(crew);
    }
    /**
     * GET /crew/{id}
     * Get by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Crew> getById(@PathVariable Long id) {
        return crewService.getCrewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /crew
     * Create new member.
     */
    @PostMapping
    public ResponseEntity<Crew> createCrew(@RequestBody CrewDTO crewDto) {
        return ResponseEntity.ok(crewService.createCrew(crewDto));
    }

    /**
     * PUT /crew/{id}
     * Update by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Crew> updateCrew(@PathVariable Long id, @RequestBody CrewDTO dto) {
        return crewService.updateCrew(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /crew/{id}
     * Delete by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long id) {
        return crewService.deleteCrew(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}