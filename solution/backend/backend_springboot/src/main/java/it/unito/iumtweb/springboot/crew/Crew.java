package it.unito.iumtweb.springboot.crew;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'crew' table in the PostgreSQL database.
 * <p>
 * Maps film crew members (directors, writers, etc.).
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "crew")
public class Crew implements Serializable {

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
     * The name of the crew member.
     */
    @Column(name = "name")
    private String name;

    /**
     * The job or role performed (e.g., "Director", "Writer").
     */
    @Column(name = "role")
    private String role;

    /** Default constructor. */
    public Crew() {}

    /**
     * Constructor for creation.
     */
    public Crew(Integer movieId, String name, String role) {
        this.movieId = movieId;
        this.name = name;
        this.role = role;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}