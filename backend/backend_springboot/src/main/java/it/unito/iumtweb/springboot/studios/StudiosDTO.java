package it.unito.iumtweb.springboot.studios;

public class StudiosDTO {

    private Long movieId;
    private String studioName;

    public StudiosDTO() {}

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }
}