package it.unito.iumtweb.springboot.genres;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Genres} entity.
 * <p>
 * A genre association is uniquely identified by the combination of the film ID ({@code movieId})
 * and the genre name ({@code genre}).
 * This class is marked as {@link Embeddable} to be used as an embedded ID in the entity.
 * </p>
 */
@Embeddable
public class GenresPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key to the Movies relation).
     */
    private Long movieId;

    /**
     * The name of the genre (e.g., "Horror", "Comedy").
     */
    private String genre;

    /**
     * Default constructor required by JPA.
     */
    public GenresPrimaryKey() {}

    /**
     * Constructs a new Primary Key with the specified movie ID and genre.
     *
     * @param movieId The ID of the film.
     * @param genre   The name of the genre.
     */
    public GenresPrimaryKey(Long movieId, String genre) {
        this.movieId = movieId;
        this.genre = genre;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

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
        GenresPrimaryKey that = (GenresPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(genre, that.genre);
    }

    /**
     * Generates a hash code based on the composite key fields.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(movieId, genre);
    }
}