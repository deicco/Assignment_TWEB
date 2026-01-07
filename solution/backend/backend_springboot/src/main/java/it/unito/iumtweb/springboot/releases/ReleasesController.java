package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Release resources.
 */
@RestController
@RequestMapping("/releases")
@CrossOrigin(origins = "http://localhost:3000")
public class ReleasesController {

    @Autowired
    private ReleasesService releasesService;

    /**
     * GET /releases
     * Retrieves releases with optional country filter.
     */
    @GetMapping
    public ResponseEntity<Page<Releases>> getAllReleases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String country) {

        Pageable pageable = PageRequest.of(page, size);

        if (country != null && !country.isEmpty()) {
            return ResponseEntity.ok(releasesService.getReleasesByCountry(country, pageable));
        }
        return ResponseEntity.ok(releasesService.getAllReleases(pageable));
    }

    /**
     * GET /releases/movie/{movieId}
     * Retrieves all releases for a specific movie.
     */
    @GetMapping("/movie/{movieId}")
    public List<Releases> getReleasesByMovieId(@PathVariable Integer movieId) {
        return releasesService.getReleasesByMovieId(movieId);
    }

    /**
     * GET /releases/{id}
     * Retrieves a specific release by unique ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Releases> getById(@PathVariable Long id) {
        return releasesService.getReleaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /releases
     * Creates a new release.
     */
    @PostMapping
    public Releases createRelease(@RequestBody ReleasesDTO releaseDto) {
        return releasesService.createRelease(releaseDto);
    }

    /**
     * PUT /releases/{id}
     * Updates an existing release by unique ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Releases> updateRelease(@PathVariable Long id, @RequestBody ReleasesDTO updatedDto) {
        return releasesService.updateRelease(id, updatedDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /releases/{id}
     * Deletes a release.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelease(@PathVariable Long id) {
        return releasesService.deleteRelease(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}