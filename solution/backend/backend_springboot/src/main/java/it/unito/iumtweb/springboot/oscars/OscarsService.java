package it.unito.iumtweb.springboot.oscars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class encapsulating business logic for Oscar management.
 */
@Service
public class OscarsService {

    private final OscarsRepository oscarsRepository;

    @Autowired
    public OscarsService(OscarsRepository oscarsRepository) {
        this.oscarsRepository = oscarsRepository;
    }
    /**
     * Retrieves all oscar entries with pagination.
     */
    public Page<Oscars> getAllOscars(Pageable pageable) {
        return oscarsRepository.findAll(pageable);
    }

    /**
     * Searches awards by film, person name, or winner status.
     */
    public Page<Oscars> searchOscars(String film, String name, Boolean onlyWinners, Pageable pageable) {
        if (Boolean.TRUE.equals(onlyWinners)) {
            return oscarsRepository.findByWinnerTrue(pageable);
        }
        if (film != null && !film.isEmpty()) {
            return oscarsRepository.findByFilmContainingIgnoreCase(film, pageable);
        }
        if (name != null && !name.isEmpty()) {
            return oscarsRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return oscarsRepository.findAll(pageable);
    }

    /**
     * Retrieves awards for a specific movie (by ID).
     */
    public List<Oscars> getOscarsByMovieId(Integer movieId) {
        return oscarsRepository.findByMovieId(movieId);
    }

    /**
     * Retrieves a specific entry by unique ID.
     */
    public Optional<Oscars> getOscarById(Long id) {
        return oscarsRepository.findById(id);
    }

    /**
     * Creates a new oscar entry.
     */
    public Oscars createOscar(OscarsDTO dto) {
        Oscars newOscar = new Oscars(
                dto.getMovieId(),
                dto.getYearFilm(),
                dto.getYearCeremony(),
                dto.getCeremony(),
                dto.getCategory(),
                dto.getName(),
                dto.getFilm(),
                dto.getWinner()
        );
        return oscarsRepository.save(newOscar);
    }

    /**
     * Updates an existing entry by unique ID.
     */
    public Optional<Oscars> updateOscar(Long id, OscarsDTO dto) {
        return oscarsRepository.findById(id)
                .map(existing -> {
                    existing.setCategory(dto.getCategory());
                    existing.setWinner(dto.getWinner());
                    existing.setName(dto.getName());
                    existing.setYearFilm(dto.getYearFilm());
                    existing.setYearCeremony(dto.getYearCeremony());
                    existing.setCeremony(dto.getCeremony());
                    existing.setFilm(dto.getFilm());

                    if (dto.getMovieId() != null) {
                        existing.setMovieId(dto.getMovieId());
                    }
                    return oscarsRepository.save(existing);
                });
    }

    /**
     * Deletes an entry by unique ID.
     */
    public boolean deleteOscar(Long id) {
        if (oscarsRepository.existsById(id)) {
            oscarsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}