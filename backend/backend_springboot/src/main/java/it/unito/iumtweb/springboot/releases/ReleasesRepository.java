package it.unito.iumtweb.springboot.releases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {

    // Esempio: trovare tutte le release per un determinato paese
    List<Releases> findByCountry(String country);
}