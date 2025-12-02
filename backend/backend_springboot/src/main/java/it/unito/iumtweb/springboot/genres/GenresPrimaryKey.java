package it.unito.iumtweb.springboot.genres;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GenresPrimaryKey implements Serializable {
    private Long id; // ID del Film
    private String genre;

    public GenresPrimaryKey() {}
    public GenresPrimaryKey(Long id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenresPrimaryKey that = (GenresPrimaryKey) o;
        return Objects.equals(id, that.id) && Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() { return Objects.hash(id, genre); }
}