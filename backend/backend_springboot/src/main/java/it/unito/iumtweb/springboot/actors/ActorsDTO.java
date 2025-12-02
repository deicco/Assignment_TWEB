package it.unito.iumtweb.springboot.actors;

public class ActorsDTO {
    private Long movieId; // NUOVO CAMPO: L'ID del film
    private String name;
    private String role;

    // Costruttore vuoto
    public ActorsDTO() {}

    // Costruttore completo
    public ActorsDTO(Long movieId, String name, String role) {
        this.movieId = movieId;
        this.name = name;
        this.role = role;
    }

    // --- Getter e Setter ---

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