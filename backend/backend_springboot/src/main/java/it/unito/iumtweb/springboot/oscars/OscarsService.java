package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class encapsulating business logic for Oscar management.
 * <p>
 * Handles data retrieval and filtering for awards analysis.
 * </p>
 */
@Service
public class OscarsService {

    @Autowired
    private OscarsRepository oscarsRepository;

    /**
     * Retrieves all oscar entries with pagination.
     */
    public Page<Oscars> getAllOscars(Pageable pageable) {
        return oscarsRepository.findAll(pageable);
    }

    /**
     * Searches awards by film, person name, or winner status.
     *
     * @param film Film name filter.
     * @param name Person name filter.
     * @param onlyWinners If true, returns only winners.
     * @param pageable Pagination info.
     * @return A {@link Page} of results.
     */
    public Page<Oscars> searchOscars(String film, String name, Boolean onlyWinners, Pageable pageable) {
        if (Boolean.TRUE.equals(onlyWinners)) {
            return oscarsRepository.findByWinnerTrue(pageable);
        }
        if (film != null && !film.isEmpty()) {
            return oscarsRepository.findByIdFilmContainingIgnoreCase(film, pageable);
        }
        if (name != null && !name.isEmpty()) {
            return oscarsRepository.findByIdNameContainingIgnoreCase(name, pageable);
        }
        return oscarsRepository.findAll(pageable);
    }

    /**
     * Retrieves a specific entry by composite key.
     */
    public Optional<Oscars> getOscarByCompositeKey(int year, String category, String film, String name) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(year, category, film, name);
        return oscarsRepository.findById(pk);
    }

    /**
     * Creates a new oscar entry.
     */
    public Oscars createOscar(OscarsDTO dto) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(dto.getYear_film(), dto.getCategory(), dto.getFilm(), dto.getName());
        Oscars newOscar = new Oscars();
        newOscar.setId(pk);
        newOscar.setYear_ceremony(dto.getYear_ceremony());
        newOscar.setCeremony(dto.getCeremony());
        newOscar.setWinner(dto.getWinner());
        return oscarsRepository.save(newOscar);
    }

    /**
     * Deletes an entry.
     */
    public boolean deleteOscar(int year, String category, String film, String name) {
        OscarsPrimaryKey pk = new OscarsPrimaryKey(year, category, film, name);
        if (oscarsRepository.existsById(pk)) {
            oscarsRepository.deleteById(pk);
            return true;
        }
        return false;
    }
}