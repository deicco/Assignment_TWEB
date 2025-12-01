package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrewService {

    @Autowired
    private CrewRepository crewRepository;

    // READ: Ottieni tutto il crew
    public List<Crew> getAllCrew() {
        return crewRepository.findAll();
    }

    // READ: Ottieni un membro del crew per ID
    public Optional<Crew> getCrewById(Long id) {
        return crewRepository.findById(id);
    }

    // CREATE/UPDATE: Salva o aggiorna un membro del crew
    public Crew saveCrew(Crew crew) {
        return crewRepository.save(crew);
    }

    // UPDATE: Aggiorna un membro del crew esistente
    public Crew updateCrew(Long id, Crew updatedCrew) {
        return crewRepository.findById(id)
                .map(existingCrew -> {
                    existingCrew.setName(updatedCrew.getName());
                    existingCrew.setRole(updatedCrew.getRole());
                    return crewRepository.save(existingCrew);
                })
                .orElse(null);
    }

    // DELETE: Elimina un membro del crew per ID
    public boolean deleteCrew(Long id) {
        if (crewRepository.existsById(id)) {
            crewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}