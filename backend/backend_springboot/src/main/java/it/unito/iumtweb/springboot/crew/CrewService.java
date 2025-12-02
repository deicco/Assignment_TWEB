package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrewService {

    @Autowired
    private CrewRepository crewRepository;

    // READ: Ottieni tutte le associazioni
    public List<Crew> getAllCrew() {
        return crewRepository.findAll();
    }

    // READ: Ottieni crew per ID Film
    public List<Crew> getCrewByMovieId(Long movieId) {
        return crewRepository.findByIdMovieId(movieId);
    }

    // READ: Ottieni un membro specifico tramite chiave composta
    public Optional<Crew> getCrewByCompositeId(Long movieId, String crewName) {
        CrewPrimaryKey pk = new CrewPrimaryKey(movieId, crewName);
        return crewRepository.findById(pk);
    }

    // CREATE: Salva un nuovo membro del crew (associazione) usando DTO
    public Crew createCrew(CrewDTO crewDto) {
        CrewPrimaryKey pk = new CrewPrimaryKey(crewDto.getMovieId(), crewDto.getCrewName());
        Crew newCrew = new Crew(pk, crewDto.getRole());
        return crewRepository.save(newCrew);
    }

    // UPDATE: Aggiorna un membro del crew esistente usando DTO e chiave
    public Crew updateCrew(Long movieId, String crewName, CrewDTO updatedCrewDto) {
        CrewPrimaryKey pk = new CrewPrimaryKey(movieId, crewName);

        return crewRepository.findById(pk)
                .map(existingCrew -> {
                    // Si aggiorna solo il campo non-chiave (role)
                    existingCrew.setRole(updatedCrewDto.getRole());
                    return crewRepository.save(existingCrew);
                })
                .orElse(null);
    }

    // DELETE: Elimina un membro del crew per chiave composta
    public boolean deleteCrew(Long movieId, String crewName) {
        CrewPrimaryKey pk = new CrewPrimaryKey(movieId, crewName);
        if (crewRepository.existsById(pk)) {
            crewRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Metodo di salvataggio generico (come da tua versione originale, mantenuto per data loading)
    public Crew saveCrew(Crew crew) {
        return crewRepository.save(crew);
    }
}