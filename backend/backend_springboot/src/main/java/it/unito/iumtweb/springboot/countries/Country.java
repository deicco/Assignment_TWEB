package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;
    // PK tecnica, NON nel CSV, aggiunta perché un film è associato in più nazioni,
    // quindi l'id del film non può essere univoco

    @Column(name = "id", nullable = false)
    private Long id;     // id del film, dal CSV

    @Column(name = "country", nullable = false)
    private String country;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
