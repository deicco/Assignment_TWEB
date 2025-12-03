package it.unito.iumtweb.springboot.genres;

/**
 * Data Transfer Object (DTO) for the Genres entity.
 * <p>
 * Used to transfer genre data between the client (Main Express Server) and this Spring Boot application.
 * Decouples the internal database structure from the external API.
 * </p>
 */
public class GenresDTO {

    /** The ID of the film. */
    private Long movieId;

    /** The genre name. */
    private String genre;

    /**
     * Default constructor.
     */
    public GenresDTO() {}

    /**
     * Full constructor.
     * @param movieId The movie ID.
     * @param genre The genre name.
     */
    public GenresDTO(Long movieId, String genre) {
        this.movieId = movieId;
        this.genre = genre;
    }

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}