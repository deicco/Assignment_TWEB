package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleasesService {

    @Autowired
    private ReleasesRepository releasesRepository;

    // READ: Ottieni tutte le release
    public List<Releases> getAllReleases() {
        return releasesRepository.findAll();
    }

    // READ: Ottieni una release per ID (presumibilmente ID del film)
    public Optional<Releases> getReleaseById(Long id) {
        return releasesRepository.findById(id);
    }

    // READ: Ottieni release per paese
    public List<Releases> getReleasesByCountry(String country) {
        return releasesRepository.findByCountry(country);
    }

    // CREATE/UPDATE: Salva o aggiorna una release
    public Releases saveRelease(Releases release) {
        return releasesRepository.save(release);
    }

    // UPDATE: Aggiorna una release esistente
    public Optional<Releases> updateRelease(Long id, Releases updatedRelease) {
        return releasesRepository.findById(id)
                .map(existingRelease -> {
                    existingRelease.setCountry(updatedRelease.getCountry());
                    existingRelease.setDate(updatedRelease.getDate());
                    existingRelease.setType(updatedRelease.getType());
                    existingRelease.setRating(updatedRelease.getRating());
                    return releasesRepository.save(existingRelease);
                });
    }

    // DELETE: Elimina una release per ID
    public boolean deleteRelease(Long id) {
        if (releasesRepository.existsById(id)) {
            releasesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}