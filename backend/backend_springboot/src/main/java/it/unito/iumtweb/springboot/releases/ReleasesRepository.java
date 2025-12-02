package it.unito.iumtweb.springboot.releases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, ReleasesPrimaryKey> {

    // Esempio: trovare tutte le release per un determinato paese (tramite campo nella PK)
    List<Releases> findByIdCountry(String country);

    // Trovare tutte le release per un film (tramite campo nella PK)
    List<Releases> findByIdId(Long movieId);
}