package it.unito.iumtweb.springboot.themes;
import jakarta.persistence.*;

@Entity
@Table(name = "themes")
public class Themes {

    @EmbeddedId
    private ThemesPrimaryKey id;

    public Themes() {}

    public ThemesPrimaryKey getId() { return id; }
    public void setId(ThemesPrimaryKey id) { this.id = id; }

    // Metodi helper opzionali per accedere ai campi della chiave
    public Long getMovieId() { return id != null ? id.getId() : null; }
    public String getTheme() { return id != null ? id.getTheme() : null; }
}