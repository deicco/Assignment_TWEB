package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Country} entity.
 * <p>
 * According to the data schema, a country association is uniquely identified by the combination
 * of the film ID ({@code movieId}) and the country name ({@code country}).
 * This class is marked as {@link Embeddable} to be used as an embedded ID in the entity.
 * </p>
 */
@Embeddable
public class CountryPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key to the Movies relation).
     */
    private Long movieId;

    /**
     * The name of the country.
     */
    private String country;

    /**
     * Default constructor required by JPA.
     */
    public CountryPrimaryKey() {}

    /**
     * Constructs a new Primary Key with the specified movie ID and country name.
     *
     * @param movieId The ID of the film.
     * @param country The name of the country.
     */
    public CountryPrimaryKey(Long movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    /**
     * Checks equality between two Primary Key objects.
     * Essential for the correct functioning of JPA with composite keys.
     *
     * @param o The object to compare.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryPrimaryKey that = (CountryPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(country, that.country);
    }

    /**
     * Generates a hash code based on the composite key fields.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(movieId, country);
    }
}