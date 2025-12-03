package it.unito.iumtweb.springboot.themes;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'themes' table.
 * <p>
 * Maps the thematic elements associated with films.
 * It uses a composite key defined in {@link ThemesPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "themes")
public class Themes {

    /** The composite primary key. */
    @EmbeddedId
    private ThemesPrimaryKey id;

    /** Default constructor. */
    public Themes() {}

    // --- Getters and Setters ---

    public ThemesPrimaryKey getId() { return id; }
    public void setId(ThemesPrimaryKey id) { this.id = id; }

    // Helper methods
    public Long getMovieId() { return id != null ? id.getMovieId() : null; }
    public String getTheme() { return id != null ? id.getTheme() : null; }
}