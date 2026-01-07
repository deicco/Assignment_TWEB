package it.unito.iumtweb.springboot.crew;

/**
 * Data Transfer Object (DTO) for the Crew entity.
 */
public class CrewDTO {

    private Long id;         // Unique ID (optional)
    private Integer movieId; // Movie ID (Integer)
    private String name;     // Crew member name
    private String role;     // Role (Director, Writer, etc.)

    public CrewDTO() {}

    public CrewDTO(Long id, Integer movieId, String name, String role) {
        this.id = id;
        this.movieId = movieId;
        this.name = name;
        this.role = role;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}