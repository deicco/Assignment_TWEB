package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Studio resources.
 * <p>
 * Exposes endpoints to query film studios.
 * Supports pagination and search.
 * </p>
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
     *
     * @param page    Page number (default 0).
     * @param size    Items per page (default 20).
     * @param keyword Optional search keyword.
     * @return A {@link Page} of {@link Studios}.
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
    public List<Studios> getStudiosByMovieId(@PathVariable Long movieId) {
        return studiosService.getStudiosByMovieId(movieId);
    }

    /**
     * GET /studios/{movieId}/{studioName}
     * Retrieves a specific association.
     */
    @GetMapping("/{movieId}/{studioName}")
    public ResponseEntity<Studios> getStudioByCompositeKey(@PathVariable Long movieId, @PathVariable String studioName) {
        Optional<Studios> studio = studiosService.getStudioByCompositeKey(movieId, studioName);
        return studio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
     * DELETE /studios/{movieId}/{studioName}
     * Deletes an association.
     */
    @DeleteMapping("/{movieId}/{studioName}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long movieId, @PathVariable String studioName) {
        boolean deleted = studiosService.deleteStudio(movieId, studioName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // PUT endpoint kept for API consistency, though functionally limited for this entity
    @PutMapping("/{movieId}/{studioName}")
    public ResponseEntity<Studios> updateStudio(
            @PathVariable Long movieId,
            @PathVariable String studioName,
            @RequestBody StudiosDTO updatedStudioDto
    ) {
        Optional<Studios> result = studiosService.updateStudio(movieId, studioName, updatedStudioDto);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}