package it.unito.iumtweb.springboot.actors;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity class representing the 'actors' table in the database.
 * <p>
 * Uses a standard auto-incrementing Primary Key (id) for easier management.
 * Links to the Movies table via 'movie_id' (Integer), matching the Movies entity definition.
 * </p>
 */
@Entity
@Table(name = "actors")
public class Actors implements Serializable {

    /**
     * Unique identifier for this actor record (Auto-increment).
     * This is the Surrogate Key used for updates and deletions.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Foreign Key: The ID of the movie this actor belongs to.
     * Mapped as Integer to match the Movies table definition.
     */
    @Column(name = "movie_id")
    private Integer movieId;

    /**
     * The name of the actor.
     */
    @Column(name = "name")
    private String name;

    /**
     * The role played by the actor.
     */
    @Column(name = "role")
    private String role;

    /** Default constructor for JPA. */
    public Actors() {}

    /**
     * Constructor for creation (without ID).
     */
    public Actors(Integer movieId, String name, String role) {
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