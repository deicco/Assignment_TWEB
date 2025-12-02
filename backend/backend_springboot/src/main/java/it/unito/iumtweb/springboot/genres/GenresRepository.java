package it.unito.iumtweb.springboot.genres;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GenresRepository extends JpaRepository<Genres, GenresPrimaryKey> {
    List<Genres> findByIdId(Long movieId); // Trova generi per film
    List<Genres> findByIdGenre(String genre); // Trova film per genere
}