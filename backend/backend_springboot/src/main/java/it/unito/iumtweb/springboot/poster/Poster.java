package it.unito.iumtweb.springboot.poster;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'posters' table.
 * <p>
 * Stores the URL links to movie posters.
 * Uses a Surrogate Key (auto-increment Long ID) and links to Movies via 'movie_id'.
 * </p>
 */
@Entity
@Table(name = "posters")
public class Poster implements Serializable {

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
     * The URL of the poster image.
     * Stored as TEXT to handle long URLs.
     */
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    /** Default constructor. */
    public Poster() {}

    /** Full constructor. */
    public Poster(Integer movieId, String link) {
        this.movieId = movieId;
        this.link = link;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}