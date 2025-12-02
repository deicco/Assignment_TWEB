package it.unito.iumtweb.springboot.crew;

public class CrewDTO {

    private Long movieId;
    private String crewName;
    private String role; // Il ruolo specifico del membro del crew

    public CrewDTO() {}

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getCrewName() { return crewName; }
    public void setCrewName(String crewName) { this.crewName = crewName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}