package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/releases")
public class ReleasesController {

    @Autowired
    private ReleasesService releasesService;

    // GET /releases
    @GetMapping
    public List<Releases> getAllReleases() {
        return releasesService.getAllReleases();
    }

    // GET /releases/country/{country}
    @GetMapping("/country/{country}")
    public List<Releases> getReleasesByCountry(@PathVariable String country) {
        return releasesService.getReleasesByCountry(country);
    }

    // GET /releases/{id} (Ricerca per ID film/PK)
    @GetMapping("/{id}")
    public ResponseEntity<Releases> getReleaseById(@PathVariable Long id) {
        Optional<Releases> release = releasesService.getReleaseById(id);
        return release.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /releases
    @PostMapping
    public Releases createRelease(@RequestBody Releases release) {
        return releasesService.saveRelease(release);
    }

    // PUT /releases/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Releases> updateRelease(@PathVariable Long id, @RequestBody Releases updatedRelease) {
        return releasesService.updateRelease(id, updatedRelease)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /releases/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelease(@PathVariable Long id) {
        return releasesService.deleteRelease(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}