package it.unito.iumtweb.springboot.studios;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'studios' table.
 * <p>
 * Maps the production studios associated with films.
 * It uses a composite key defined in {@link StudiosPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "studios")
public class Studios {

    /** The composite primary key. */
    @EmbeddedId
    private StudiosPrimaryKey id;

    /** Default constructor. */
    public Studios() {}

    // --- Getters and Setters ---

    public StudiosPrimaryKey getId() { return id; }
    public void setId(StudiosPrimaryKey id) { this.id = id; }

    // Helper methods for direct access
    public Long getMovieId() { return id != null ? id.getMovieId() : null; }
    public String getStudio() { return id != null ? id.getStudioName() : null; }
}