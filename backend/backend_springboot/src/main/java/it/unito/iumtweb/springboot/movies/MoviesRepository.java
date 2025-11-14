package it.unito.iumtweb.springboot.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MoviesRepository extends JpaRepository<Movies, Integer> {
    // Filtra per nome (contiene, case‐insensitive)
    Page<Movies> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Filtra per range di rating
    Page<Movies> findByRatingBetween(float min, float max, Pageable pageable);

    // Filtra per range di data
    Page<Movies> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
