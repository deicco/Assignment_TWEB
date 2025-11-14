package it.unito.iumtweb.springboot.actors;

import it.unito.iumtweb.springboot.actors.Actors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActorsRepository extends JpaRepository<Actors, Integer> {
}
