package it.unito.iumtweb.springboot.crew;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Crew} entity.
 * <p>
 * According to the data schema, a crew member is uniquely identified by the combination
 * of the film ID ({@code movieId}) and the person's name ({@code crewName}).
 * This class is marked as {@link Embeddable} to be used as an embedded ID in the entity.
 * </p>
 */
@Embeddable
public class CrewPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key to the Movies relation).
     */
    private Long movieId;

    /**
     * The name of the crew member.
     */
    private String crewName;

    /**
     * Default constructor required by JPA.
     */
    public CrewPrimaryKey() {}

    /**
     * Constructs a new Primary Key with the specified movie ID and crew name.
     *
     * @param movieId  The ID of the film.
     * @param crewName The name of the crew member.
     */
    public CrewPrimaryKey(Long movieId, String crewName) {
        this.movieId = movieId;
        this.crewName = crewName;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getCrewName() { return crewName; }
    public void setCrewName(String crewName) { this.crewName = crewName; }

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
        CrewPrimaryKey that = (CrewPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(crewName, that.crewName);
    }

    /**
     * Generates a hash code based on the composite key fields.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() { return Objects.hash(movieId, crewName); }
}