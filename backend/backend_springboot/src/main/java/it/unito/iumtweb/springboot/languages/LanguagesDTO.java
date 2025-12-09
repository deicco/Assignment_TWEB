package it.unito.iumtweb.springboot.languages;

/**
 * Data Transfer Object (DTO) for the Languages entity.
 */
public class LanguagesDTO {

    private Long id;         // Unique ID (optional)
    private Integer movieId; // Movie ID (Integer)
    private String language; // Language name
    private String type;     // Type (optional)

    public LanguagesDTO() {}

    public LanguagesDTO(Long id, Integer movieId, String language, String type) {
        this.id = id;
        this.movieId = movieId;
        this.language = language;
        this.type = type;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}