package it.unito.iumtweb.springboot.studios;
import jakarta.persistence.*;

@Entity
@Table(name = "studios")
public class Studios {

    @EmbeddedId // Usa la chiave composta
    private StudiosPrimaryKey id;

    public Studios() {}

    // --- Getters e Setters ---

    public StudiosPrimaryKey getId() { return id; }
    public void setId(StudiosPrimaryKey id) { this.id = id; }

    // I metodi per movieId e studioName sono ora nella PK
    public Long getMovieId() { return id.getMovieId(); }
    public String getStudio() { return id.getStudioName(); }

    // Non sono necessari setter qui per i campi della chiave
}