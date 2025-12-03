package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Release resources.
 * <p>
 * Exposes endpoints to query release dates and ratings.
 * Supports pagination to handle the large data volume.
 * </p>
 */
@RestController
@RequestMapping("/releases")
@CrossOrigin(origins = "http://localhost:3000")
public class ReleasesController {

    @Autowired
    private ReleasesService releasesService;

    /**
     * GET /releases
     * Retrieves all releases with pagination.
     *
     * @param page Page number (default 0).
     * @param size Items per page (default 20).
     * @param country Optional country filter.
     * @return A {@link Page} of {@link Releases}.
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
     * GET /releases/movie/{id}
     * Retrieves all releases for a specific movie.
     */
    @GetMapping("/movie/{id}")
    public List<Releases> getReleasesByMovieId(@PathVariable Long id) {
        return releasesService.getReleasesByMovieId(id);
    }

    /**
     * GET /releases/{movieId}/{country}
     * Retrieves a specific release.
     */
    @GetMapping("/{movieId}/{country}")
    public ResponseEntity<Releases> getReleaseByCompositeKey(
            @PathVariable Long movieId,
            @PathVariable String country) {
        Optional<Releases> release = releasesService.getReleaseByCompositeKey(movieId, country);
        return release.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
     * PUT /releases/{movieId}/{country}
     * Updates an existing release.
     */
    @PutMapping("/{movieId}/{country}")
    public ResponseEntity<Releases> updateRelease(
            @PathVariable Long movieId,
            @PathVariable String country,
            @RequestBody ReleasesDTO updatedDto) {
        return releasesService.updateRelease(movieId, country, updatedDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /releases/{movieId}/{country}
     * Deletes a release.
     */
    @DeleteMapping("/{movieId}/{country}")
    public ResponseEntity<Void> deleteRelease(
            @PathVariable Long movieId,
            @PathVariable String country) {
        return releasesService.deleteRelease(movieId, country)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}