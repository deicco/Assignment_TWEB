package it.unito.iumtweb.springboot.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorsRepository extends JpaRepository<Actors, ActorsPrimaryKey> {

    // Cerca tutti gli attori per l'ID del film
    List<Actors> findByIdId(Long movieId);

    // Cerca tutte le apparizioni di un attore (per nome, che fa parte della chiave)
    List<Actors> findByIdName(String name);
}