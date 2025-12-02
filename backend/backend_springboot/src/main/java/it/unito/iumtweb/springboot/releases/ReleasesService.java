package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleasesService {

    @Autowired
    private ReleasesRepository releasesRepository;

    // READ: Ottieni tutte le release (Invariato)
    public List<Releases> getAllReleases() {
        return releasesRepository.findAll();
    }

    // READ: Ottieni release per ID del film (Nuovo)
    public List<Releases> getReleasesByMovieId(Long movieId) {
        return releasesRepository.findByIdId(movieId);
    }

    // READ: Ottieni release per chiave composta (Nuovo)
    public Optional<Releases> getReleaseByCompositeKey(Long movieId, String country) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(movieId, country);
        return releasesRepository.findById(pk);
    }

    // READ: Ottieni release per paese (Modificato per usare il repository)
    public List<Releases> getReleasesByCountry(String country) {
        return releasesRepository.findByIdCountry(country);
    }

    // CREATE: Salva o aggiorna una release usando DTO (Modificato)
    public Releases createRelease(ReleasesDTO releaseDto) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(releaseDto.getId(), releaseDto.getCountry());
        Releases newRelease = new Releases();

        // Mappatura DTO -> Entity
        newRelease.setId(pk);
        newRelease.setDate(releaseDto.getDate());
        newRelease.setType(releaseDto.getType());
        newRelease.setRating(releaseDto.getRating());

        return releasesRepository.save(newRelease);
    }

    // UPDATE: Aggiorna una release esistente usando DTO e chiave composta (Modificato)
    public Optional<Releases> updateRelease(Long movieId, String country, ReleasesDTO updatedDto) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(movieId, country);

        return releasesRepository.findById(pk)
                .map(existingRelease -> {
                    // Aggiorna solo i campi non-chiave
                    existingRelease.setDate(updatedDto.getDate());
                    existingRelease.setType(updatedDto.getType());
                    existingRelease.setRating(updatedDto.getRating());
                    return releasesRepository.save(existingRelease);
                });
    }

    // DELETE: Elimina una release per chiave composta (Modificato)
    public boolean deleteRelease(Long movieId, String country) {
        ReleasesPrimaryKey pk = new ReleasesPrimaryKey(movieId, country);
        if (releasesRepository.existsById(pk)) {
            releasesRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Metodo originale saveRelease mantenuto per data loading o uso generico
    public Releases saveRelease(Releases release) {
        return releasesRepository.save(release);
    }
}