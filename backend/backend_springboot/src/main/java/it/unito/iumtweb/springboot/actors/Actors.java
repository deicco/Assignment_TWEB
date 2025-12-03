package it.unito.iumtweb.springboot.actors;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * JPA Entity representing the 'actors' table in the PostgreSQL database.
 * <p>
 * This class maps the static data regarding actors and their roles in specific films.
 * It uses a composite key defined in {@link ActorsPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "actors")
public class Actors {

    /**
     * The composite primary key (Movie ID + Actor Name).
     */
    @EmbeddedId
    private ActorsPrimaryKey id;

    /**
     * The character or role played by the actor in the specified film.
     */
    private String role;

    /**
     * Default constructor.
     */
    public Actors() {}

    // --- Getters and Setters for the ID ---

    /**
     * Retrieves the composite primary key.
     * @return The {@link ActorsPrimaryKey} instance.
     */
    public ActorsPrimaryKey getId() {
        return id;
    }

    /**
     * Sets the composite primary key.
     * @param id The new {@link ActorsPrimaryKey}.
     */
    public void setId(ActorsPrimaryKey id) {
        this.id = id;
    }

    // --- Getters and Setters for the Role ---

    /**
     * Retrieves the role of the actor.
     * @return The role description.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the actor.
     * @param role The new role description.
     */
    public void setRole(String role) {
        this.role = role;
    }

    // --- Helper Methods (Optional) ---

    /**
     * Utility method to retrieve the actor's name directly from the embedded ID.
     * @return The actor's name or {@code null} if the ID is not set.
     */
    public String getName() {
        return id != null ? id.getName() : null;
    }

    /**
     * Utility method to retrieve the movie ID directly from the embedded ID.
     * @return The movie ID or {@code null} if the ID is not set.
     */
    public Long getMovieId() {
        return id != null ? id.getId() : null;
    }
}