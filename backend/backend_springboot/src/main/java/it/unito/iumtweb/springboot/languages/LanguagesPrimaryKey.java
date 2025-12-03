package it.unito.iumtweb.springboot.languages;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Languages} entity.
 * <p>
 * A language association is uniquely identified by the combination of the film ID ({@code movieId})
 * and the language name ({@code language}).
 * This class is marked as {@link Embeddable} to be used as an embedded ID in the entity.
 * </p>
 */
@Embeddable
public class LanguagesPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key to the Movies relation).
     * Changed from int to Long to match other entities.
     */
    private Long movieId;

    /**
     * The name of the language (e.g., "English", "Italian").
     */
    private String language;

    /**
     * Default constructor required by JPA.
     */
    public LanguagesPrimaryKey() {}

    /**
     * Constructs a new Primary Key.
     *
     * @param movieId  The ID of the film.
     * @param language The name of the language.
     */
    public LanguagesPrimaryKey(Long movieId, String language) {
        this.movieId = movieId;
        this.language = language;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    /**
     * Checks equality between two Primary Key objects.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguagesPrimaryKey that = (LanguagesPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(language, that.language);
    }

    /**
     * Generates a hash code based on the composite key fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(movieId, language);
    }
}