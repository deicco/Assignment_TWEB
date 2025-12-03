package it.unito.iumtweb.springboot.oscars;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'oscars' table.
 * <p>
 * Maps the additional dataset regarding Academy Awards (Oscars).
 * Used by journalists/experts to analyze winners and nominees.
 * </p>
 */
@Entity
@Table(name = "oscars")
public class Oscars {

    /** The composite primary key. */
    @EmbeddedId
    private OscarsPrimaryKey id;

    private int year_ceremony;
    private int ceremony;

    // Note: 'name' is now part of the Primary Key, so it's not a separate field here.

    private Boolean winner;

    /** Default constructor. */
    public Oscars() {}

    // --- Getters and Setters ---

    public OscarsPrimaryKey getId() { return id; }
    public void setId(OscarsPrimaryKey id) { this.id = id; }

    public int getYear_film() { return id != null ? id.getYear_film() : 0; }
    public String getCategory() { return id != null ? id.getCategory() : null; }
    public String getFilm() { return id != null ? id.getFilm() : null; }
    public String getName() { return id != null ? id.getName() : null; }

    public int getYear_ceremony() { return year_ceremony; }
    public void setYear_ceremony(int year_ceremony) { this.year_ceremony = year_ceremony; }

    public int getCeremony() { return ceremony; }
    public void setCeremony(int ceremony) { this.ceremony = ceremony; }

    public Boolean getWinner() { return winner; }
    public void setWinner(Boolean winner) { this.winner = winner; }
}