package it.unito.iumtweb.springboot.studios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudiosRepository extends JpaRepository<Studios, StudiosPrimaryKey> {

    // Metodo per trovare tutti gli studi di un film (tramite ID nella PK)
    List<Studios> findByIdMovieId(Long movieId);

    // Metodo per trovare tutte le associazioni per nome dello studio
    List<Studios> findByIdStudioName(String studioName);
}