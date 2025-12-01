package it.unito.iumtweb.springboot.poster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostersService {

    @Autowired
    private PosterRepository posterRepository;

    // READ: Ottieni tutti i poster
    public List<Poster> getAllPosters() {
        return posterRepository.findAll();
    }

    // READ: Ottieni un poster per ID (presumibilmente ID del film)
    public Optional<Poster> getPosterById(Long id) {
        return posterRepository.findById(id);
    }

    // CREATE/UPDATE: Salva o aggiorna un poster
    public Poster savePoster(Poster poster) {
        return posterRepository.save(poster);
    }

    // UPDATE: Aggiorna un poster esistente
    public Poster updatePoster(Long id, Poster updatedPoster) {
        return posterRepository.findById(id)
                .map(existingPoster -> {
                    existingPoster.setLink(updatedPoster.getLink());
                    return posterRepository.save(existingPoster);
                })
                .orElse(null);
    }

    // DELETE: Elimina un poster per ID
    public boolean deletePoster(Long id) {
        if (posterRepository.existsById(id)) {
            posterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}