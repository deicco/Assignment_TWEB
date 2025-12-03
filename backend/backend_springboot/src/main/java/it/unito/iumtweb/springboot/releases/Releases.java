package it.unito.iumtweb.springboot.releases;

import java.util.Date;
import jakarta.persistence.*;

/**
 * JPA Entity representing the 'releases' table.
 * <p>
 * Contains data about film release dates, types (Theatrical/Digital), and ratings
 * in different countries.
 * This is the largest dataset (13M+ rows), so performance is critical.
 * </p>
 */
@Entity
@Table(name = "releases")
public class Releases {

    /** The composite primary key. */
    @EmbeddedId
    private ReleasesPrimaryKey id;

    /** The release date. */
    private Date date;

    /** The release type (e.g., "Theatrical", "Digital"). */
    private String type;

    /** The rating certification in that country (e.g., "PG-13", "R"). */
    private Float rating;

    /** Default constructor. */
    public Releases() {}

    // --- Getters and Setters ---

    public ReleasesPrimaryKey getId() {return id;}
    public void setId(ReleasesPrimaryKey id) { this.id = id; }

    // Helper methods for direct access
    public Long getMovieId() { return id != null ? id.getMovieId() : null; }
    public String getCountry() { return id != null ? id.getCountry() : null; }

    public Date getDate() {return date;}
    public void setDate(Date date) { this.date = date; }

    public String getType() {return type;}
    public void setType(String type) { this.type = type; }

    public Float getRating() {return rating;}
    public void setRating(Float rating) { this.rating = rating; }
}