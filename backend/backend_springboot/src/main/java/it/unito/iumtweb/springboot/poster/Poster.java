package it.unito.iumtweb.springboot.poster;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'posters' table.
 * <p>
 * Stores the URL links to movie posters.
 * Since the dataset size matches the movies dataset (approx 940k),
 * we assume a One-to-One relationship where the ID is the Movie ID.
 * </p>
 */
@Entity
@Table(name = "posters")
public class Poster {

    /**
     * The Movie ID. Acts as both Primary Key and Foreign Key to Movies.
     */
    @Id
    private Long id;

    /**
     * The URL of the poster image.
     * Stored as TEXT to handle long URLs.
     */
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    /** Default constructor. */
    public Poster() {}

    /** Full constructor. */
    public Poster(Long id, String link) {
        this.id = id;
        this.link = link;
    }

    // --- Getters and Setters ---

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getLink() {return link;}
    public void setLink(String link) { this.link = link; }
}