package it.unito.iumtweb.springboot.genres;

import jakarta.persistence.*;

/**
 * JPA Entity representing the 'genres' table in the PostgreSQL database.
 * <p>
 * This class maps the static data regarding film genres.
 * It uses a composite key defined in {@link GenresPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "genres")
public class Genres {

    /**
     * The composite primary key (Movie ID + Genre Name).
     */
    @EmbeddedId
    private GenresPrimaryKey id;

    /**
     * Default constructor.
     */
    public Genres() {}

    /**
     * Retrieves the composite primary key.
     * @return The {@link GenresPrimaryKey} instance.
     */
    public GenresPrimaryKey getId() { return id; }

    /**
     * Sets the composite primary key.
     * @param id The new {@link GenresPrimaryKey}.
     */
    public void setId(GenresPrimaryKey id) { this.id = id; }

    // --- Helper methods for direct access ---

    /**
     * Gets the movie ID directly from the key.
     * @return The movie ID.
     */
    public Long getMovieId() {
        return id != null ? id.getMovieId() : null;
    }

    /**
     * Gets the genre name directly from the key.
     * @return The genre name.
     */
    public String getGenre() {
        return id != null ? id.getGenre() : null;
    }
}