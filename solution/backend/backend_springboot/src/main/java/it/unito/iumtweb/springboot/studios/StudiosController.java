package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Studio resources.
 */
@RestController
@RequestMapping("/studios")
@CrossOrigin(origins = "http://localhost:3000")
public class StudiosController {

    @Autowired
    private StudiosService studiosService;

    /**
     * GET /studios
     * Retrieves all studios with pagination and optional search.
     */
    @GetMapping
    public ResponseEntity<Page<Studios>> getAllStudios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(studiosService.searchStudios(keyword, pageable));
        }
        return ResponseEntity.ok(studiosService.getAllStudios(pageable));
    }

    /**
     * GET /studios/movie/{movieId}
     * Retrieves all studios for a specific movie.
     */
    @GetMapping("/movie/{movieId}")
    public List<Studios> getStudiosByMovieId(@PathVariable Integer movieId) {
        return studiosService.getStudiosByMovieId(movieId);
    }

    /**
     * GET /studios/{id}
     * Retrieves a specific entry by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Studios> getById(@PathVariable Long id) {
        return studiosService.getStudioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /studios
     * Creates a new association.
     */
    @PostMapping
    public Studios createStudio(@RequestBody StudiosDTO studioDto) {
        return studiosService.createStudio(studioDto);
    }

    /**
     * PUT /studios/{id}
     * Updates an existing entry by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Studios> updateStudio(@PathVariable Long id, @RequestBody StudiosDTO updatedDto) {
        return studiosService.updateStudio(id, updatedDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /studios/{id}
     * Deletes an association by unique ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        return studiosService.deleteStudio(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}