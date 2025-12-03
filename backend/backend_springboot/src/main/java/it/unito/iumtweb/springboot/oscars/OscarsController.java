package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST Controller for managing Oscar resources.
 * <p>
 * Exposes endpoints to query awards data.
 * Supports pagination and filtering (e.g., show only winners).
 * </p>
 */
@RestController
@RequestMapping("/oscars")
@CrossOrigin(origins = "http://localhost:3000")
public class OscarsController {

    @Autowired
    private OscarsService oscarsService;

    /**
     * GET /oscars
     * Retrieves awards with optional filters.
     *
     * @param film Optional film name search.
     * @param name Optional nominee name search.
     * @param onlyWinners If true, returns only winners.
     * @param page Page number (default 0).
     * @param size Items per page (default 20).
     * @return A {@link Page} of {@link Oscars}.
     */
    @GetMapping
    public ResponseEntity<Page<Oscars>> getOscars(
            @RequestParam(required = false) String film,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean onlyWinners,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(oscarsService.searchOscars(film, name, onlyWinners, pageable));
    }

    /**
     * POST /oscars
     * Creates a new entry.
     */
    @PostMapping
    public Oscars createOscar(@RequestBody OscarsDTO oscarDto) {
        return oscarsService.createOscar(oscarDto);
    }

    // Nota: Ho semplificato rimuovendo il GET/DELETE by ID complesso dall'URL
    // perché con 4 parametri di chiave diventa scomodo da gestire via REST standard.
    // La ricerca e creazione coprono i casi d'uso principali.
}