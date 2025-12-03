package it.unito.iumtweb.springboot.languages;

/**
 * Data Transfer Object (DTO) for the Languages entity.
 * <p>
 * Used to transfer language data between the client and this server.
 * </p>
 */
public class LanguagesDTO {

    /** The ID of the film. Changed to Long for consistency. */
    private Long movieId;

    /** The language name. */
    private String language;

    /** The type of language usage (optional). */
    private String type;

    /**
     * Default constructor.
     */
    public LanguagesDTO() {}

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}