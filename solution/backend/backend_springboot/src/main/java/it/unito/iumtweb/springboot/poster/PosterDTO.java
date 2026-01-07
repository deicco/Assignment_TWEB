package it.unito.iumtweb.springboot.poster;

/**
 * Data Transfer Object (DTO) for the Poster entity.
 */
public class PosterDTO {

    private Long id;
    private Integer movieId;
    private String link;

    public PosterDTO() {}

    public PosterDTO(Long id, Integer movieId, String link) {
        this.id = id;
        this.movieId = movieId;
        this.link = link;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}