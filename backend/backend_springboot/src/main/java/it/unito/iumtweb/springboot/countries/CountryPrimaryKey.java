package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CountryPrimaryKey implements Serializable {

    private Long movieId; // Rinominato da 'id' a 'movieId' per chiarezza e coerenza
    private String country;

    public CountryPrimaryKey() {}

    public CountryPrimaryKey(Long movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    // --- equals() e hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryPrimaryKey that = (CountryPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, country);
    }
}