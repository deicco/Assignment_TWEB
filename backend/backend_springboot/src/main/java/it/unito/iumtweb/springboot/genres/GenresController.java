package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public List<Genres> getAllGenres() { return genresService.getAllGenres(); }

    @GetMapping("/movie/{id}")
    public List<Genres> getGenresByMovie(@PathVariable Long id) {
        return genresService.getGenresByMovieId(id);
    }

    @PostMapping
    public Genres createGenre(@RequestBody GenresDTO dto) {
        return genresService.createGenre(dto);
    }

    @DeleteMapping("/{id}/{genre}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id, @PathVariable String genre) {
        return genresService.deleteGenre(id, genre) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}