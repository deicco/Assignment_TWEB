package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Oscar resources.
 */
@RestController
@RequestMapping("/oscars")
@CrossOrigin(origins = "http://localhost:3000")
public class OscarsController {

    @Autowired
    private OscarsService oscarsService;

    /**
     * GET /oscars
     * Retrieves awards with optional filters.
     */
    @GetMapping
    public ResponseEntity<Page<Oscars>> getOscars(
            @RequestParam(required = false) String film,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean onlyWinners,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(oscarsService.searchOscars(film, name, onlyWinners, pageable));
    }

    /**
     * GET /oscars/movie/{movieId}
     * Retrieves awards for a specific movie.
     */
    @GetMapping("/movie/{movieId}")
    public List<Oscars> getOscarsByMovieId(@PathVariable Integer movieId) {
        return oscarsService.getOscarsByMovieId(movieId);
    }

    /**
     * GET /oscars/{id}
     * Retrieves a specific entry by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Oscars> getById(@PathVariable Long id) {
        return oscarsService.getOscarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /oscars
     * Creates a new entry.
     */
    @PostMapping
    public ResponseEntity<Oscars> createOscar(@RequestBody OscarsDTO oscarDto) {
        return ResponseEntity.ok(oscarsService.createOscar(oscarDto));
    }

    /**
     * PUT /oscars/{id}
     * Updates an existing entry.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Oscars> updateOscar(@PathVariable Long id, @RequestBody OscarsDTO oscarDto) {
        return oscarsService.updateOscar(id, oscarDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /oscars/{id}
     * Deletes an entry.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOscar(@PathVariable Long id) {
        return oscarsService.deleteOscar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}