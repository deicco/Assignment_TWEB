package it.unito.iumtweb.springboot.themes;

/**
 * Data Transfer Object (DTO) for the Themes entity.
 */
public class ThemesDTO {

    private Long id;
    private Integer movieId;
    private String theme;

    public ThemesDTO() {}

    public ThemesDTO(Long id, Integer movieId, String theme) {
        this.id = id;
        this.movieId = movieId;
        this.theme = theme;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}