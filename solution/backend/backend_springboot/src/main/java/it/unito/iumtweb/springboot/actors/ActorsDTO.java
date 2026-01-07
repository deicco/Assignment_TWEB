package it.unito.iumtweb.springboot.actors;

/**
 * Data Transfer Object (DTO) for the Actors entity.
 * <p>
 * Used to transfer actor data between the client and the server.
 * </p>
 */
public class ActorsDTO {

    private Long id;         // The unique ID of the record (optional in creation)
    private Integer movieId; // The ID of the movie (Integer to match Movies Entity)
    private String name;     // Actor name
    private String role;     // Actor role

    public ActorsDTO() {}

    public ActorsDTO(Long id, Integer movieId, String name, String role) {
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