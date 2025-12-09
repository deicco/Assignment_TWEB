package it.unito.iumtweb.springboot.studios;

/**
 * Data Transfer Object (DTO) for the Studios entity.
 */
public class StudiosDTO {

    private Long id;
    private Integer movieId;
    private String studioName;

    public StudiosDTO() {}

    public StudiosDTO(Long id, Integer movieId, String studioName) {
        this.id = id;
        this.movieId = movieId;
        this.studioName = studioName;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }
}