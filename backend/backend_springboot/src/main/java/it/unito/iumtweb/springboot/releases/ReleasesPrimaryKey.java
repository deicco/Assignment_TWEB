package it.unito.iumtweb.springboot.releases;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Releases} entity.
 * <p>
 * According to the data schema, a release is uniquely identified by the combination
 * of the film ID ({@code movieId}) and the country of release ({@code country}).
 * </p>
 */
@Embeddable
public class ReleasesPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key).
     */
    private Long movieId;

    /**
     * The country where the film was released.
     */
    private String country;

    /** Default constructor. */
    public ReleasesPrimaryKey() {}

    /** Full constructor. */
    public ReleasesPrimaryKey(Long movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    // --- equals and hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleasesPrimaryKey that = (ReleasesPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, country);
    }
}