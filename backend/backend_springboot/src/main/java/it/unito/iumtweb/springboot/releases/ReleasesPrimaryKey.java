package it.unito.iumtweb.springboot.releases;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReleasesPrimaryKey implements Serializable {

    private Long id; // ID del Film
    private String country;

    public ReleasesPrimaryKey() {}

    public ReleasesPrimaryKey(Long id, String country) {
        this.id = id;
        this.country = country;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    // --- equals() e hashCode() (obbligatori) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleasesPrimaryKey that = (ReleasesPrimaryKey) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }
}