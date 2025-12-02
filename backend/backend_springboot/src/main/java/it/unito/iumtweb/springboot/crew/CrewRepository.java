package it.unito.iumtweb.springboot.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewRepository extends JpaRepository<Crew, CrewPrimaryKey> {

    // Cerca tutti i membri del crew per l'ID del film
    List<Crew> findByIdMovieId(Long movieId);

    // Cerca tutti i film di un membro del crew (tramite crewName, che fa parte della chiave)
    List<Crew> findByIdCrewName(String crewName);
}