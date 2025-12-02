package it.unito.iumtweb.springboot.languages;
import jakarta.persistence.*;

@Entity
@Table(name = "languages")
public class Languages {

    @EmbeddedId
    private LanguagesPrimaryKey id;

    private String type; // Campo descrittivo (non chiave)

    public Languages() {}

    public LanguagesPrimaryKey getId() { return id; }
    public void setId(LanguagesPrimaryKey id) { this.id = id; }

    public int getMovieId() { return id.getId(); }
    public String getLanguage() { return id.getLanguage(); }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public void setLanguage(String language) {
    }
}