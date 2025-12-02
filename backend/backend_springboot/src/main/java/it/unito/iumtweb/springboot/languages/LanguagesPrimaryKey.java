package it.unito.iumtweb.springboot.languages;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LanguagesPrimaryKey implements Serializable {
    private int id; // ID del Film (Nota: era int nel file originale)
    private String language;

    public LanguagesPrimaryKey() {}
    public LanguagesPrimaryKey(int id, String language) {
        this.id = id;
        this.language = language;
    }
    // Getters, Setters, Equals, HashCode...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguagesPrimaryKey that = (LanguagesPrimaryKey) o;
        return id == that.id && Objects.equals(language, that.language);
    }
    @Override
    public int hashCode() { return Objects.hash(id, language); }
}