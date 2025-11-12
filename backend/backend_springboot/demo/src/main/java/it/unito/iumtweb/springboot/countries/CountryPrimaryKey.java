package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CountryPrimaryKey implements Serializable {
    private Integer id;
    private String country;

    public CountryPrimaryKey(Long id, String country) {}

    public CountryPrimaryKey(Integer id, String country) {
        this.id = id;
        this.country = country;
    }

    public Integer getMovie() {
        return id;
    }
    public void setMovie(Integer id) {
        this.id = id;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryPrimaryKey countryPrimaryKey = (CountryPrimaryKey) o;
        return Objects.equals(id, countryPrimaryKey.id)
                && Objects.equals(country, countryPrimaryKey.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }
}