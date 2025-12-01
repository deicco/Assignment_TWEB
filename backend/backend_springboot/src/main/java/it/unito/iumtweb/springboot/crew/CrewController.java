package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crew")
public class CrewController {

    @Autowired
    private CrewService crewService;

    // GET /crew
    @GetMapping
    public List<Crew> getAllCrew() {
        return crewService.getAllCrew();
    }

    // GET /crew/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Crew> getCrewById(@PathVariable Long id) {
        Optional<Crew> crew = crewService.getCrewById(id);
        return crew.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /crew
    @PostMapping
    public Crew createCrew(@RequestBody Crew crew) {
        // Poiché non c'è @GeneratedValue, l'ID deve essere fornito nel JSON
        return crewService.saveCrew(crew);
    }

    // PUT /crew/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Crew> updateCrew(@PathVariable Long id, @RequestBody Crew updatedCrew) {
        Crew result = crewService.updateCrew(id, updatedCrew);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /crew/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long id) {
        boolean deleted = crewService.deleteCrew(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}