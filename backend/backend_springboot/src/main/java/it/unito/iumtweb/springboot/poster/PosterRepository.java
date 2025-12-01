package it.unito.iumtweb.springboot.poster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {

    // Non sono necessari metodi findByLink, dato che l'ID è la PK
}