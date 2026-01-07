package it.unito.iumtweb.springboot.genres;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'genres' table in the PostgreSQL database.
 * <p>
 * Maps film genres (e.g., Horror, Comedy).
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "genres")
public class Genres implements Serializable {

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
     * The name of the genre (e.g., "Horror", "Comedy").
     */
    @Column(name = "genre")
    private String genre;

    /** Default constructor. */
    public Genres() {}

    /**
     * Constructor for creation.
     */
    public Genres(Integer movieId, String genre) {
        this.movieId = movieId;
        this.genre = genre;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}