package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostersService {

    @Autowired
    private PosterRepository posterRepository;

    // READ: Ottieni tutti i poster (Invariato)
    public List<Poster> getAllPosters() {
        return posterRepository.findAll();
    }

    // READ: Ottieni un poster per ID (presumibilmente ID del film) (Invariato)
    public Optional<Poster> getPosterById(Long id) {
        return posterRepository.findById(id);
    }

    // CREATE: Salva un nuovo poster usando DTO e ID del film
    public Poster createPoster(Long movieId, PosterDTO posterDto) {
        Poster newPoster = new Poster();
        // L'ID è l'ID del film
        newPoster.setId(movieId);
        newPoster.setLink(posterDto.getLink());
        return posterRepository.save(newPoster);
    }

    // CREATE/UPDATE: Salva o aggiorna un poster (Metodo originale, mantenuto se serve)
    public Poster savePoster(Poster poster) {
        return posterRepository.save(poster);
    }

    // UPDATE: Aggiorna un poster esistente usando DTO
    public Poster updatePoster(Long id, PosterDTO updatedPosterDto) {
        return posterRepository.findById(id)
                .map(existingPoster -> {
                    // Aggiorna solo il campo non-chiave 'link'
                    existingPoster.setLink(updatedPosterDto.getLink());
                    return posterRepository.save(existingPoster);
                })
                .orElse(null);
    }

    // DELETE: Elimina un poster per ID (Invariato)
    public boolean deletePoster(Long id) {
        if (posterRepository.existsById(id)) {
            posterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}