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

    // GET /movies (Invariato)
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

    // GET /movies/{id} (Invariato)
    @GetMapping("/{id}")
    public ResponseEntity<Movies> getOne(@PathVariable Long id) {
        return service.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /movies (Ora usa DTO)
    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody MoviesDTO movieDto) {
        Movies saved = service.createMovie(movieDto);
        return ResponseEntity.ok(saved);
    }

    // PUT /movies/{id} (Ora usa DTO)
    @PutMapping("/{id}")
    public ResponseEntity<Movies> update(
            @PathVariable Long id,
            @RequestBody MoviesDTO movieDto
    ) {
        return service.updateMovie(id, movieDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /movies/{id} (Invariato)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteMovie(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}