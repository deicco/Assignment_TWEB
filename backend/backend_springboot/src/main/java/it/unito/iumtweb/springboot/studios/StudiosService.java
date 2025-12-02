package it.unito.iumtweb.springboot.studios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudiosService {

    @Autowired
    private StudiosRepository studiosRepository;

    // READ: Ottieni tutte le associazioni studio-film
    public List<Studios> getAllStudios() {
        return studiosRepository.findAll();
    }

    // READ: Ottieni uno studio per ID film (Nuovo)
    public List<Studios> getStudiosByMovieId(Long movieId) {
        return studiosRepository.findByIdMovieId(movieId);
    }

    // READ: Ottieni associazione specifica per chiave composta (Nuovo)
    public Optional<Studios> getStudioByCompositeKey(Long movieId, String studioName) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(movieId, studioName);
        return studiosRepository.findById(pk);
    }

    // CREATE: Salva una nuova associazione studio-film usando DTO
    public Studios createStudio(StudiosDTO studioDto) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(studioDto.getMovieId(), studioDto.getStudioName());
        Studios newStudio = new Studios();
        newStudio.setId(pk);
        return studiosRepository.save(newStudio);
    }

    // UPDATE: Aggiorna uno studio esistente (Non ha campi non-chiave da aggiornare, quindi si usa solo per creare/sovrascrivere)
    // Non ha senso implementare un update che cambi solo i campi non-chiave, poiché non ce ne sono.
    // Lo mantengo come semplice salvataggio DTO se necessario, ma i Dati di Studio non cambiano.
    public Optional<Studios> updateStudio(Long movieId, String studioName, StudiosDTO updatedDto) {
        // Poiché non ci sono campi non-chiave in Studios, una PUT/Update qui sarebbe solo una ri-creazione o non ha effetto.
        // Assumiamo che si voglia solo verificare l'esistenza e ri-salvare (anche se i campi sono gli stessi).
        StudiosPrimaryKey pk = new StudiosPrimaryKey(movieId, studioName);

        return studiosRepository.findById(pk)
                .map(existingStudio -> {
                    // Non essendoci campi non-chiave, l'aggiornamento è una no-op
                    return existingStudio;
                });
        // In una vera applicazione, si potrebbe usare un DTO più ampio o non fornire un endpoint PUT per questa entità.
    }

    // DELETE: Elimina uno studio per chiave composta
    public boolean deleteStudio(Long movieId, String studioName) {
        StudiosPrimaryKey pk = new StudiosPrimaryKey(movieId, studioName);
        if (studiosRepository.existsById(pk)) {
            studiosRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Metodo di salvataggio originale mantenuto per data loading o uso generico
    public Studios saveStudio(Studios studio) {
        return studiosRepository.save(studio);
    }
}