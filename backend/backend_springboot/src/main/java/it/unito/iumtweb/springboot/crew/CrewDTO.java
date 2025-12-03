package it.unito.iumtweb.springboot.crew;

/**
 * Data Transfer Object (DTO) for the Crew entity.
 * <p>
 * Used to transfer crew data between the client (Main Express Server) and this Spring Boot application.
 * This pattern decouples the internal database structure from the external API.
 * </p>
 */
public class CrewDTO {

    /** The ID of the film. */
    private Long movieId;

    /** The name of the crew member. */
    private String crewName;

    /** The specific role (e.g., Director). */
    private String role;

    /**
     * Default constructor.
     */
    public CrewDTO() {}

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getCrewName() { return crewName; }
    public void setCrewName(String crewName) { this.crewName = crewName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}