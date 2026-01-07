package it.unito.iumtweb.springboot.movies;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'movies' table.
 * <p>
 * This is the central entity of the system. It contains the main metadata for films.
 * It uses a standard numerical Primary Key (id) which is referenced by foreign keys in Actors, Crew, etc.
 * </p>
 */
@Entity
@Table(name = "movies")
public class Movies implements Serializable {

    // 1. Definiamo un generatore di sequenza con il nome standard di Postgres
    @SequenceGenerator(name = "movies_seq", sequenceName = "movies_id_seq", allocationSize = 1)

    // 2. Usiamo la strategia SEQUENCE.
    // IMPORTANTE: Usiamo Long invece di Integer per compatibilità con JpaRepository<Movies, Long>
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_seq")
    private Long id;

    /** The title of the movie. */
    private String name;

    /**
     * The release year.
     * Mapped as Integer because the CSV/DB contains only the year (e.g., 2024).
     */
    @Column(name = "date")
    private Integer date;

    /** The promotional slogan of the movie. */
    private String tagline;

    /**
     * A short story abstract or plot summary.
     * Stored as TEXT to allow long descriptions.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** The duration of the movie in minutes. */
    private Integer minute;

    /** The average rating score. */
    private Float rating;

    /**
     * Default constructor.
     */
    public Movies() {}

    /**
     * Full constructor.
     */
    public Movies(Long id, String name, Integer date, String tagline, String description, Integer minute, Float rating) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.tagline = tagline;
        this.description = description;
        this.minute = minute;
        this.rating = rating;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDate() { return date; }
    public void setDate(Integer date) { this.date = date; }

    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getMinute() { return minute; }
    public void setMinute(Integer minute) { this.minute = minute; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}