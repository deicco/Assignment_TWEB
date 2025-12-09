package it.unito.iumtweb.springboot.releases;

import java.util.Date;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'releases' table.
 * <p>
 * Contains data about film release dates, types, and ratings.
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "releases")
public class Releases implements Serializable {

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

    /** The release date. */
    @Column(name = "date")
    private Date date;

    /** The country where the film was released. */
    @Column(name = "country")
    private String country;

    /** The release type (e.g., "Theatrical", "Digital"). */
    @Column(name = "type")
    private String type;

    /** The rating certification in that country. */
    @Column(name = "rating")
    private Float rating;

    /** Default constructor. */
    public Releases() {}

    /** Full constructor. */
    public Releases(Integer movieId, String country, Date date, String type, Float rating) {
        this.movieId = movieId;
        this.country = country;
        this.date = date;
        this.type = type;
        this.rating = rating;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}