package it.unito.iumtweb.springboot.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesService service;

    // ... (listMovies resta invariato) ...
    @GetMapping
    public Page<Movies> listMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float maxRating,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getMovies(name, minRating, maxRating, startDate, endDate, pageable);
    }

    // Aggiornato: da int a Long
    @GetMapping("/{id}")
    public ResponseEntity<Movies> getOne(@PathVariable Long id) {
        return service.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ... (create resta invariato) ...
    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody Movies movie) {
        Movies saved = service.createMovie(movie);
        return ResponseEntity.ok(saved);
    }

    // Aggiornato: da int a Long
    @PutMapping("/{id}")
    public ResponseEntity<Movies> update(
            @PathVariable Long id,
            @RequestBody Movies movie
    ) {
        return service.updateMovie(id, movie)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aggiornato: da int a Long
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteMovie(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}