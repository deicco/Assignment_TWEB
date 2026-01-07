package it.unito.iumtweb.springboot.languages;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'languages' table.
 * <p>
 * Maps languages spoken in films.
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "languages")
public class Languages implements Serializable {

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
     * The name of the language (e.g., "English", "Italian").
     */
    @Column(name = "language")
    private String language;

    /**
     * Optional descriptive field (e.g., "Original", "Dubbed").
     */
    @Column(name = "type")
    private String type;

    /** Default constructor. */
    public Languages() {}

    /**
     * Constructor for creation.
     */
    public Languages(Integer movieId, String language, String type) {
        this.movieId = movieId;
        this.language = language;
        this.type = type;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}