package it.unito.iumtweb.springboot.themes;

/**
 * Data Transfer Object (DTO) for the Themes entity.
 * <p>
 * Transfers theme data between client and server.
 * </p>
 */
public class ThemesDTO {

    private Long movieId; // Renamed from 'id' for clarity
    private String theme;

    public ThemesDTO() {}

    public ThemesDTO(Long movieId, String theme) {
        this.movieId = movieId;
        this.theme = theme;
    }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}