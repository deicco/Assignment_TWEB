package it.unito.iumtweb.springboot.themes;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'themes' table.
 * <p>
 * Maps the thematic elements associated with films.
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "themes")
public class Themes implements Serializable {

    /**
     * Unique identifier for this record (Auto-increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Foreign Key: The ID of the movie.
     * Mapped as Integer to match the Movies table definition.
     */
    @Column(name = "movie_id")
    private Integer movieId;

    /**
     * The theme description.
     */
    @Column(name = "theme")
    private String theme;

    /** Default constructor. */
    public Themes() {}

    /** Full constructor. */
    public Themes(Integer movieId, String theme) {
        this.movieId = movieId;
        this.theme = theme;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}