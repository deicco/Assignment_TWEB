package it.unito.iumtweb.springboot.genres;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genres {

    @EmbeddedId
    private GenresPrimaryKey id;

    // Non ci sono altri campi oltre alla chiave (il CSV è solo id, genre)

    public Genres() {}

    public GenresPrimaryKey getId() { return id; }
    public void setId(GenresPrimaryKey id) { this.id = id; }

    // Helper per accesso diretto
    public Long getMovieId() { return id.getId(); }
    public String getGenre() { return id.getGenre(); }
}