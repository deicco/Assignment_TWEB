package it.unito.iumtweb.springboot.oscars;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Oscars} entity.
 * <p>
 * To ensure uniqueness in the Oscar dataset, the key must include:
 * - The year of the film
 * - The category
 * - The film name
 * - The nominee's name
 * This prevents duplicates when multiple actors from the same film are nominated in the same category.
 * </p>
 */
@Embeddable
public class OscarsPrimaryKey implements Serializable {

    private int year_film;
    private String category;
    private String film;
    private String name; // Added to ensure uniqueness

    /** Default constructor. */
    public OscarsPrimaryKey() {}

    /** Full constructor. */
    public OscarsPrimaryKey(int year_film, String category, String film, String name) {
        this.year_film = year_film;
        this.category = category;
        this.film = film;
        this.name = name;
    }

    // --- Getters and Setters ---

    public int getYear_film() { return year_film; }
    public void setYear_film(int year_film) { this.year_film = year_film; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFilm() { return film; }
    public void setFilm(String film) { this.film = film; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // --- equals and hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OscarsPrimaryKey that = (OscarsPrimaryKey) o;
        return year_film == that.year_film &&
                Objects.equals(category, that.category) &&
                Objects.equals(film, that.film) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year_film, category, film, name);
    }
}