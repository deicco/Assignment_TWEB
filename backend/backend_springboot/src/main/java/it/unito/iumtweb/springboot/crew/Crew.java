package it.unito.iumtweb.springboot.crew;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * JPA Entity representing the 'crew' table in the PostgreSQL database.
 * <p>
 * This class maps the static data regarding film crew members (directors, writers, etc.).
 * It uses a composite key defined in {@link CrewPrimaryKey}.
 * </p>
 */
@Entity
@Table(name = "crew")
public class Crew {

    /**
     * The composite primary key (Movie ID + Crew Name).
     */
    @EmbeddedId
    private CrewPrimaryKey id;

    /**
     * The job or role performed by the person (e.g., "Director", "Writer").
     */
    private String role;

    /**
     * Default constructor.
     */
    public Crew() {}

    /**
     * Full constructor.
     * @param id The composite ID.
     * @param role The role description.
     */
    public Crew(CrewPrimaryKey id, String role) {
        this.id = id;
        this.role = role;
    }

    // --- Getters and Setters ---

    /**
     * Retrieves the composite primary key.
     * @return The {@link CrewPrimaryKey} instance.
     */
    public CrewPrimaryKey getId() {
        return id;
    }

    /**
     * Sets the composite primary key.
     * @param id The new {@link CrewPrimaryKey}.
     */
    public void setId(CrewPrimaryKey id) {
        this.id = id;
    }

    /**
     * Retrieves the role of the crew member.
     * @return The role description.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the crew member.
     * @param role The new role description.
     */
    public void setRole(String role) {
        this.role = role;
    }

    // Helper to get name directly
    public String getName() {
        return id != null ? id.getCrewName() : null;
    }
}