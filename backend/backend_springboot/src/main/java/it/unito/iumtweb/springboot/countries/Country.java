package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'countries' table in the PostgreSQL database.
 * <p>
 * This class maps the static data regarding the production countries of films.
 * It uses a composite key defined in {@link CountryPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "countries")
public class Country {

    /**
     * The composite primary key (Movie ID + Country Name).
     */
    @EmbeddedId
    private CountryPrimaryKey id;

    /**
     * Default constructor.
     */
    public Country() {}

    /**
     * Retrieves the composite primary key.
     * @return The {@link CountryPrimaryKey} instance.
     */
    public CountryPrimaryKey getId() { return id; }

    /**
     * Sets the composite primary key.
     * @param id The new {@link CountryPrimaryKey}.
     */
    public void setId(CountryPrimaryKey id) { this.id = id; }

    // Helper method to get Country Name directly
    public String getCountryName() {
        return id != null ? id.getCountry() : null;
    }
}