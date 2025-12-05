package it.unito.iumtweb.springboot.movies;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;

/**
 * JPA Entity representing the 'movies' table.
 * <p>
 * This is the central entity of the system. It contains the main metadata for films.
 * Unlike other entities, this uses a standard numerical Primary Key ({@code id})
 * which is referenced by foreign keys in Actors, Crew, etc.
 * </p>
 */
@Entity
@Table(name = "movies")
public class Movies {

        // 1. Definiamo un generatore di sequenza con il nome standard di Postgres
        @SequenceGenerator(name = "movies_seq", sequenceName = "movies_id_seq", allocationSize = 1)

        @Id
        // 2. Usiamo la strategia SEQUENCE e puntiamo al generatore definito sopra
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_seq")
        private Long id;

    /** The title of the movie. */
    private String name;

    /** The release date. */
    @Column(name = "date")
    private LocalDate date;

    /** The promotional slogan of the movie. */
    private String tagline;

    /**
     * A short story abstract or plot summary.
     * Stored as TEXT to allow long descriptions.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** The duration of the movie in minutes. */
    private int minute;

    /** The average rating score. */
    private Float rating;

    /**
     * Default constructor.
     */
    public Movies() {}

    /**
     * Full constructor.
     */
    public Movies(Long id, String name, LocalDate date, String tagline, String description, int minute, float rating) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.tagline = tagline;
        this.description = description;
        this.minute = minute;
        this.rating = rating;
    }

    // --- Getters and Setters ---

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) { this.date = date; }

    public String getTagline() {return tagline;}
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getDescription() {return description;}
    public void setDescription(String description) { this.description = description; }

    public int getMinute() {return minute;}
    public void setMinute(int minute) { this.minute = minute; }

    public float getRating() {return rating;}
    public void setRating(float rating) { this.rating = rating; }
}