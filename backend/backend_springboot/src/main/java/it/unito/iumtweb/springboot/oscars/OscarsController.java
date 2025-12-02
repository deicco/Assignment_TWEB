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

    // GET /oscars/film/{filmName} - Ricerca per nome del film (Invariato)
    @GetMapping("/film/{filmName}")
    public List<Oscars> getOscarsByFilm(@PathVariable String filmName) {
        return oscarsService.getOscarsByFilm(filmName);
    }

    // GET /oscars/{year_film}/{category}/{film} - Ricerca per Chiave Composta (Nuovo)
    @GetMapping("/{year_film}/{category}/{film}")
    public ResponseEntity<Oscars> getOscarByCompositeKey(
            @PathVariable int year_film,
            @PathVariable String category,
            @PathVariable String film
    ) {
        Optional<Oscars> oscar = oscarsService.getOscarByCompositeKey(year_film, category, film);
        return oscar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /oscars (Ora usa DTO)
    @PostMapping
    public Oscars createOscar(@RequestBody OscarsDTO oscarDto) {
        return oscarsService.createOscar(oscarDto);
    }

    // PUT /oscars/{year_film}/{category}/{film} (Nuovo)
    @PutMapping("/{year_film}/{category}/{film}")
    public ResponseEntity<Oscars> updateOscar(
            @PathVariable int year_film,
            @PathVariable String category,
            @PathVariable String film,
            @RequestBody OscarsDTO updatedDto
    ) {
        Oscars result = oscarsService.updateOscar(year_film, category, film, updatedDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // DELETE /oscars/{year_film}/{category}/{film} (Modificato)
    @DeleteMapping("/{year_film}/{category}/{film}")
    public ResponseEntity<Void> deleteOscar(
            @PathVariable int year_film,
            @PathVariable String category,
            @PathVariable String film
    ) {
        boolean deleted = oscarsService.deleteOscar(year_film, category, film);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}