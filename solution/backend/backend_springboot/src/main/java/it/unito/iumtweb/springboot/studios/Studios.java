package it.unito.iumtweb.springboot.studios;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'studios' table.
 * <p>
 * Maps the production studios associated with films.
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "studios")
public class Studios implements Serializable {

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
     * The name of the production studio.
     * Mapped to 'studio_name' column (renamed by Python script).
     */
    @Column(name = "studio_name")
    private String studioName;

    /** Default constructor. */
    public Studios() {}

    /** Full constructor. */
    public Studios(Integer movieId, String studioName) {
        this.movieId = movieId;
        this.studioName = studioName;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }
}