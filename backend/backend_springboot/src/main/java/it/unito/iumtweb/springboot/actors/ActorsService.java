package it.unito.iumtweb.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorsService {

    @Autowired
    private ActorsRepository actorsRepository;

    // READ: Ottieni tutti gli attori
    public List<Actors> getAllActors() {
        return actorsRepository.findAll();
    }

    // READ: Ottieni attori per ID Film
    public List<Actors> getActorsByMovieId(Long movieId) {
        return actorsRepository.findByIdId(movieId);
    }

    // READ: Ottieni un attore specifico tramite chiave composta
    public Optional<Actors> getActorByCompositeId(Long movieId, String actorName) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(movieId, actorName);
        return actorsRepository.findById(pk);
    }

    // CREATE: Salva un nuovo attore (associazione) usando DTO
    public Actors createActor(ActorsDTO actorDto) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(actorDto.getRole(), actorDto.getName());
        Actors newActor = new Actors();
        newActor.setId(pk);
        newActor.setRole(actorDto.getRole());
        return actorsRepository.save(newActor);
    }

    // UPDATE: Aggiorna il ruolo di un attore esistente
    public Actors updateActor(Long movieId, String actorName, ActorsDTO updatedActorDto) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(movieId, actorName);
        return actorsRepository.findById(pk)
                .map(existingActor -> {
                    // Aggiorna solo il campo non-chiave 'role'
                    existingActor.setRole(updatedActorDto.getRole());
                    return actorsRepository.save(existingActor);
                })
                .orElse(null);
    }

    // DELETE: Elimina attore per chiave composta
    public boolean deleteActor(Long movieId, String actorName) {
        ActorsPrimaryKey pk = new ActorsPrimaryKey(movieId, actorName);
        if (actorsRepository.existsById(pk)) {
            actorsRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Metodo save generico (mantenuto)
    public Actors saveActor(Actors actor) {
        return actorsRepository.save(actor);
    }
}