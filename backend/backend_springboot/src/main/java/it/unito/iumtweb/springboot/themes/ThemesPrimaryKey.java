package it.unito.iumtweb.springboot.themes;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link Themes} entity.
 * <p>
 * A theme association is uniquely identified by the combination of the film ID ({@code movieId})
 * and the theme name ({@code theme}).
 * </p>
 */
@Embeddable
public class ThemesPrimaryKey implements Serializable {

    /** The unique identifier of the film. */
    private Long movieId;

    /** The theme description. */
    private String theme;

    /** Default constructor. */
    public ThemesPrimaryKey() {}

    /** Full constructor. */
    public ThemesPrimaryKey(Long movieId, String theme) {
        this.movieId = movieId;
        this.theme = theme;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    // --- equals and hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemesPrimaryKey that = (ThemesPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(theme, that.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, theme);
    }
}