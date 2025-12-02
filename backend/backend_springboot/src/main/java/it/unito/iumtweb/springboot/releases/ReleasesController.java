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

    // GET /releases (Invariato)
    @GetMapping
    public List<Releases> getAllReleases() {
        return releasesService.getAllReleases();
    }

    // GET /releases/country/{country} (Invariato)
    @GetMapping("/country/{country}")
    public List<Releases> getReleasesByCountry(@PathVariable String country) {
        return releasesService.getReleasesByCountry(country);
    }

    // GET /releases/movie/{id} - Ricerca per ID del film (Nuovo)
    @GetMapping("/movie/{id}")
    public List<Releases> getReleasesByMovieId(@PathVariable Long id) {
        return releasesService.getReleasesByMovieId(id);
    }

    // GET /releases/{movieId}/{country} - Ricerca per Chiave Composta (Nuovo)
    @GetMapping("/{movieId}/{country}")
    public ResponseEntity<Releases> getReleaseByCompositeKey(
            @PathVariable Long movieId,
            @PathVariable String country
    ) {
        Optional<Releases> release = releasesService.getReleaseByCompositeKey(movieId, country);
        return release.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /releases (Ora usa DTO)
    @PostMapping
    public Releases createRelease(@RequestBody ReleasesDTO releaseDto) {
        return releasesService.createRelease(releaseDto);
    }

    // PUT /releases/{movieId}/{country} (Modificato per chiave composta e DTO)
    @PutMapping("/{movieId}/{country}")
    public ResponseEntity<Releases> updateRelease(
            @PathVariable Long movieId,
            @PathVariable String country,
            @RequestBody ReleasesDTO updatedDto
    ) {
        return releasesService.updateRelease(movieId, country, updatedDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /releases/{movieId}/{country} (Modificato per chiave composta)
    @DeleteMapping("/{movieId}/{country}")
    public ResponseEntity<Void> deleteRelease(
            @PathVariable Long movieId,
            @PathVariable String country
    ) {
        return releasesService.deleteRelease(movieId, country)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}