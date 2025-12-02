package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OscarsService {

    @Autowired
    private OscarsRepository oscarsRepository;

    // READ: Ottieni tutte le nomination (Invariato)
    public List<Oscars> getAllOscars() {
        return oscarsRepository.findAll();
    }

    // READ: Ottieni nomination per chiave composta (Nuova firma)
    public Optional<Oscars> getOscarByCompositeKey(int year_film, String category, String film) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(year_film, category, film);
        return oscarsRepository.findById(pk);
    }

    // READ: Cerca nomination per nome del film (Modificato per usare il repository)
    public List<Oscars> getOscarsByFilm(String filmName) {
        return oscarsRepository.findByIdFilmContainingIgnoreCase(filmName);
    }

    // CREATE: Salva una nuova nomination usando il DTO (Modificato)
    public Oscars createOscar(OscarsDTO oscarDto) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(
                oscarDto.getYear_film(),
                oscarDto.getCategory(),
                oscarDto.getFilm()
        );
        Oscars newOscar = new Oscars();
        newOscar.setId(pk);
        newOscar.setYear_ceremony(oscarDto.getYear_ceremony());
        newOscar.setCeremony(oscarDto.getCeremony());
        newOscar.setName(oscarDto.getName());
        newOscar.setWinner(oscarDto.getWinner());
        return oscarsRepository.save(newOscar);
    }

    // UPDATE: Aggiorna i campi non-chiave di una nomination (Nuovo metodo)
    public Oscars updateOscar(int year_film, String category, String film, OscarsDTO updatedDto) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(year_film, category, film);

        return oscarsRepository.findById(pk)
                .map(existingOscar -> {
                    existingOscar.setYear_ceremony(updatedDto.getYear_ceremony());
                    existingOscar.setCeremony(updatedDto.getCeremony());
                    existingOscar.setName(updatedDto.getName());
                    existingOscar.setWinner(updatedDto.getWinner());
                    return oscarsRepository.save(existingOscar);
                })
                .orElse(null);
    }

    // DELETE: Elimina una nomination per chiave composta (Modificato)
    public boolean deleteOscar(int year_film, String category, String film) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(year_film, category, film);
        if (oscarsRepository.existsById(pk)) {
            oscarsRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}