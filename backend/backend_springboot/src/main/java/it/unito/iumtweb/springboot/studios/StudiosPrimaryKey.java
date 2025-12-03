package it.unito.iumtweb.springboot.studios;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Studios} entity.
 * <p>
 * A studio association is uniquely identified by the combination of the film ID ({@code movieId})
 * and the studio name ({@code studioName}).
 * This class is marked as {@link Embeddable} to be used as an embedded ID in the entity.
 * </p>
 */
@Embeddable
public class StudiosPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key to the Movies relation).
     */
    private Long movieId;

    /**
     * The name of the production studio.
     */
    private String studioName;

    /** Default constructor. */
    public StudiosPrimaryKey() {}

    /** Full constructor. */
    public StudiosPrimaryKey(Long movieId, String studioName) {
        this.movieId = movieId;
        this.studioName = studioName;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }

    // --- equals and hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudiosPrimaryKey that = (StudiosPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(studioName, that.studioName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, studioName);
    }
}