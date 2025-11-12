package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenresController {

    @Autowired
    private GenresRepository genresRepository;

    // GET - Recupera tutti i generi con paginazione
    @GetMapping
    public Page<Genres> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        System.out.println("Request ricevuta su /genres - Page: " + page + " Size: " + size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Genres> genresPage = genresRepository.findAll(pageable);
        System.out.println("Generi trovati: " + genresPage.getTotalElements());
        return genresPage;
    }
}
