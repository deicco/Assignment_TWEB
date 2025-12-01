package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudiosService {

    @Autowired
    private StudiosRepository studiosRepository;

    // READ: Ottieni tutti gli studi
    public List<Studios> getAllStudios() {
        return studiosRepository.findAll();
    }

    // READ: Ottieni uno studio per ID
    public Optional<Studios> getStudioById(Long id) {
        return studiosRepository.findById(id);
    }

    // CREATE/UPDATE: Salva o aggiorna uno studio
    public Studios saveStudio(Studios studio) {
        return studiosRepository.save(studio);
    }

    // UPDATE: Aggiorna uno studio esistente
    public Studios updateStudio(Long id, Studios updatedStudio) {
        return studiosRepository.findById(id)
                .map(existingStudio -> {
                    existingStudio.setStudio(updatedStudio.getStudio());
                    return studiosRepository.save(existingStudio);
                })
                .orElse(null);
    }

    // DELETE: Elimina uno studio per ID
    public boolean deleteStudio(Long id) {
        if (studiosRepository.existsById(id)) {
            studiosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}