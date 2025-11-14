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

    /**
     * Esempio di chiamata:
     * GET /movies?page=0&size=20
     * GET /movies?name=shawshank&page=0&size=10
     * GET /movies?minRating=8.5&maxRating=10&page=0&size=5
     * GET /movies?startDate=1994-09-01T00:00:00&endDate=1995-01-01T00:00:00&page=0&size=10
     */
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

    @GetMapping("/{id}")
    public ResponseEntity<Movies> getOne(@PathVariable int id) {
        return service.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody Movies movie) {
        Movies saved = service.createMovie(movie);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movies> update(
            @PathVariable int id,
            @RequestBody Movies movie
    ) {
        return service.updateMovie(id, movie)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteMovie(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
