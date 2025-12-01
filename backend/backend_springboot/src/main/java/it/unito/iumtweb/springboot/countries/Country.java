package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId; // PK tecnica autoincrementante

    @Column(name = "id", nullable = false)
    private Long movieId; // Questo è l'id del film proveniente dal CSV

    @Column(name = "country", nullable = false)
    private String country;

    // Getters and Setters

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}