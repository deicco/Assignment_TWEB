package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studios")
public class StudiosController {

    @Autowired
    private StudiosService studiosService;

    // GET /studios
    @GetMapping
    public List<Studios> getAllStudios() {
        return studiosService.getAllStudios();
    }

    // GET /studios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Studios> getStudioById(@PathVariable Long id) {
        Optional<Studios> studio = studiosService.getStudioById(id);
        return studio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /studios
    @PostMapping
    public Studios createStudio(@RequestBody Studios studio) {
        return studiosService.saveStudio(studio);
    }

    // PUT /studios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Studios> updateStudio(@PathVariable Long id, @RequestBody Studios updatedStudio) {
        Studios result = studiosService.updateStudio(id, updatedStudio);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /studios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        boolean deleted = studiosService.deleteStudio(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}