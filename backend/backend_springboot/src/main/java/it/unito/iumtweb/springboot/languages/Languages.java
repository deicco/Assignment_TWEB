package it.unito.iumtweb.springboot.languages;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'languages' table.
 * <p>
 * This class maps the static data regarding languages spoken in films.
 * It uses a composite key defined in {@link LanguagesPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "languages")
public class Languages {

    /**
     * The composite primary key (Movie ID + Language Name).
     */
    @EmbeddedId
    private LanguagesPrimaryKey id;

    /**
     * Optional descriptive field (e.g., "Original", "Dubbed").
     */
    private String type;

    /**
     * Default constructor.
     */
    public Languages() {}

    /**
     * Retrieves the composite primary key.
     */
    public LanguagesPrimaryKey getId() { return id; }

    /**
     * Sets the composite primary key.
     */
    public void setId(LanguagesPrimaryKey id) { this.id = id; }

    // --- Helper methods for direct access ---

    public Long getMovieId() { return id != null ? id.getMovieId() : null; }
    public String getLanguage() { return id != null ? id.getLanguage() : null; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}