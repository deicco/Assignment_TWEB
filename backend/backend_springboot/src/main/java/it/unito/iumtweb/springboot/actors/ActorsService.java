package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorsService {

    @Autowired
    private ActorsRepository actorsRepository;

    // Ottieni tutti gli attori
    public List<Actors> getAllActors() {
        return actorsRepository.findAll();
    }

    // Ottieni attore per ID
    public Optional<Actors> getActorById(Long id) {
        return actorsRepository.findById(id);
    }

    // Crea o salva un attore
    public Actors saveActor(Actors actor) {
        return actorsRepository.save(actor);
    }

    // Aggiorna attore
    public Actors updateActor(Long id, Actors updatedActor) {
        return actorsRepository.findById(id)
                .map(existingActor -> {
                    existingActor.setName(updatedActor.getName());
                    existingActor.setRole(updatedActor.getRole());
                    return actorsRepository.save(existingActor);
                })
                .orElse(null); // Ritorna null se l'attore non esiste
    }

    // Elimina attore
    public boolean deleteActor(Long id) {
        if (actorsRepository.existsById(id)) {
            actorsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}