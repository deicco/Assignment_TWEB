package it.unito.iumtweb.springboot.actors;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 * Represents the composite primary key for the {@link Actors} entity.
 * <p>
 * According to the data schema, an actor is uniquely identified by the combination
 * of the film ID ({@code id}) and the actor's name ({@code name}).
 * This class is marked as {@link Embeddable} to be used as an embedded ID in the entity.
 * </p>
 */
@Embeddable
public class ActorsPrimaryKey implements Serializable {

    /**
     * The unique identifier of the film (Foreign Key to the Movies relation).
     */
    private Long id;

    /**
     * The name of the actor.
     */
    private String name;

    /**
     * Default constructor required by JPA.
     */
    public ActorsPrimaryKey() {}

    /**
     * Constructs a new Primary Key with the specified movie ID and actor name.
     *
     * @param id   The ID of the film.
     * @param name The name of the actor.
     */
    public ActorsPrimaryKey(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * Checks equality between two Primary Key objects.
     * Essential for the correct functioning of JPA with composite keys.
     *
     * @param o The object to compare.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorsPrimaryKey that = (ActorsPrimaryKey) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    /**
     * Generates a hash code based on the composite key fields.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}