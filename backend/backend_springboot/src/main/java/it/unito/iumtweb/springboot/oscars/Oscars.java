package it.unito.iumtweb.springboot.oscars;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity representing the 'oscars' table.
 * <p>
 * Maps the additional dataset regarding Academy Awards (Oscars).
 * Uses a Surrogate Key (auto-increment Long ID) for efficient management.
 * </p>
 */
@Entity
@Table(name = "oscars")
public class Oscars implements Serializable {

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

    @Column(name = "year_film")
    private int yearFilm;

    @Column(name = "year_ceremony")
    private int yearCeremony;

    private int ceremony;

    private String category;

    /** Name of the nominee (person). */
    private String name;

    /** Name of the film (textual). */
    private String film;

    private Boolean winner;

    /** Default constructor. */
    public Oscars() {}

    /** Full constructor. */
    public Oscars(Integer movieId, int yearFilm, int yearCeremony, int ceremony, String category, String name, String film, Boolean winner) {
        this.movieId = movieId;
        this.yearFilm = yearFilm;
        this.yearCeremony = yearCeremony;
        this.ceremony = ceremony;
        this.category = category;
        this.name = name;
        this.film = film;
        this.winner = winner;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public int getYearFilm() { return yearFilm; }
    public void setYearFilm(int yearFilm) { this.yearFilm = yearFilm; }

    public int getYearCeremony() { return yearCeremony; }
    public void setYearCeremony(int yearCeremony) { this.yearCeremony = yearCeremony; }

    public int getCeremony() { return ceremony; }
    public void setCeremony(int ceremony) { this.ceremony = ceremony; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFilm() { return film; }
    public void setFilm(String film) { this.film = film; }

    public Boolean getWinner() { return winner; }
    public void setWinner(Boolean winner) { this.winner = winner; }
}