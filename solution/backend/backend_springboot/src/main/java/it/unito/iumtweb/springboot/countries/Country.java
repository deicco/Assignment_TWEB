package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'countries' table in the PostgreSQL database.
 * <p>
 * Updated to use a Surrogate Key (auto-increment Long ID) instead of a composite key.
 * Maps the production countries of films.
 * </p>
 */
@Entity
@Table(name = "countries")
public class Country implements Serializable {

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
     * The name of the country.
     */
    @Column(name = "country")
    private String country;

    /**
     * Default constructor.
     */
    public Country() {}

    /**
     * Constructor for creation (without ID).
     */
    public Country(Integer movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}