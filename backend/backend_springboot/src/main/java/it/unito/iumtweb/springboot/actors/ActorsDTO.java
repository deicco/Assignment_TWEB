package it.unito.iumtweb.springboot.actors;

/**
 * Data Transfer Object (DTO) for the Actors entity.
 * <p>
 * Used to transfer actor data between the client (Main Express Server) and this Spring Boot application.
 * This pattern decouples the internal database structure (specifically the Composite Key) from the external API.
 * </p>
 */
public class ActorsDTO {

    /** The ID of the film associated with the actor. */
    private Long movieId;

    /** The name of the actor. */
    private String name;

    /** The role played by the actor. */
    private String role;

    /**
     * Default no-argument constructor.
     */
    public ActorsDTO() {}

    /**
     * Full constructor for creating a DTO instance.
     *
     * @param movieId The ID of the film.
     * @param name    The name of the actor.
     * @param role    The role played.
     */
    public ActorsDTO(Long movieId, String name, String role) {
        this.movieId = movieId;
        this.name = name;
        this.role = role;
    }

    // --- Getters and Setters ---

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}