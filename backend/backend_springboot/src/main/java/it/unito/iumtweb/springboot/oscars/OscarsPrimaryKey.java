package it.unito.iumtweb.springboot.oscars;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OscarsPrimaryKey implements Serializable {

    private int year_film;
    private String category;
    private String film;

    public OscarsPrimaryKey() {}

    public OscarsPrimaryKey(int year_film, String category, String film) {
        this.year_film = year_film;
        this.category = category;
        this.film = film;
    }

    // --- Getters and Setters (necessari) ---

    public int getYear_film() { return year_film; }
    public void setYear_film(int year_film) { this.year_film = year_film; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFilm() { return film; }
    public void setFilm(String film) { this.film = film; }

    // --- equals() e hashCode() (obbligatori) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OscarsPrimaryKey that = (OscarsPrimaryKey) o;
        return year_film == that.year_film &&
                Objects.equals(category, that.category) &&
                Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year_film, category, film);
    }
}