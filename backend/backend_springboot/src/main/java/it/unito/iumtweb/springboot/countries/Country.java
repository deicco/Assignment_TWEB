package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {

    @EmbeddedId
    private CountryPrimaryKey id;

    public Country() {}

    public CountryPrimaryKey getId() { return id; }
    public void setId(CountryPrimaryKey id) { this.id = id; }
}