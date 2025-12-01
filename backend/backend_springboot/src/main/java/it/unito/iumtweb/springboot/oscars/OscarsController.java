package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oscars")
public class OscarsController {

    @Autowired
    private OscarsService oscarsService;

    // GET /oscars
    @GetMapping
    public List<Oscars> getAllOscars() {
        return oscarsService.getAllOscars();
    }

    // GET /oscars/film/{filmName} - Ricerca per nome del film
    @GetMapping("/film/{filmName}")
    public List<Oscars> getOscarsByFilm(@PathVariable String filmName) {
        return oscarsService.getOscarsByFilm(filmName);
    }

    // GET /oscars/{year_film} - Ricerca per PK (anno del film)
    @GetMapping("/{year_film}")
    public ResponseEntity<Oscars> getOscarByFilmYear(@PathVariable int year_film) {
        Optional<Oscars> oscar = oscarsService.getOscarByFilmYear(year_film);
        return oscar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /oscars
    @PostMapping
    public Oscars createOscar(@RequestBody Oscars oscar) {
        return oscarsService.saveOscar(oscar);
    }

    // DELETE /oscars/{year_film}
    @DeleteMapping("/{year_film}")
    public ResponseEntity<Void> deleteOscar(@PathVariable int year_film) {
        boolean deleted = oscarsService.deleteOscar(year_film);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}