package it.unito.iumtweb.springboot.studios;

/**
 * Data Transfer Object (DTO) for the Studios entity.
 * <p>
 * Transfers studio data between client and server.
 * </p>
 */
public class StudiosDTO {

    private Long movieId;
    private String studioName;

    public StudiosDTO() {}

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }
}