package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    public List<Genres> getAllGenres() { return genresRepository.findAll(); }

    public List<Genres> getGenresByMovieId(Long movieId) {
        return genresRepository.findByIdId(movieId);
    }

    public Genres createGenre(GenresDTO dto) {
        GenresPrimaryKey pk = new GenresPrimaryKey(dto.getId(), dto.getGenre());
        Genres genre = new Genres();
        genre.setId(pk);
        return genresRepository.save(genre);
    }

    public boolean deleteGenre(Long movieId, String genre) {
        GenresPrimaryKey pk = new GenresPrimaryKey(movieId, genre);
        if (genresRepository.existsById(pk)) {
            genresRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    // Metodo per il caricamento dati
    public void saveGenre(Genres genre) {
        genresRepository.save(genre);
    }
}