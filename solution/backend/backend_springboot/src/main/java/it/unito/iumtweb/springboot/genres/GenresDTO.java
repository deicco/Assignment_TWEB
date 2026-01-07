package it.unito.iumtweb.springboot.genres;

/**
 * Data Transfer Object (DTO) for the Genres entity.
 */
public class GenresDTO {

    private Long id;         // Unique ID (optional)
    private Integer movieId; // Movie ID (Integer)
    private String genre;    // Genre name

    public GenresDTO() {}

    public GenresDTO(Long id, Integer movieId, String genre) {
        this.id = id;
        this.movieId = movieId;
        this.genre = genre;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}