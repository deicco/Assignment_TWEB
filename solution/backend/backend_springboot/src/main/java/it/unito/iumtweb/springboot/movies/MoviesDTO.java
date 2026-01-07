package it.unito.iumtweb.springboot.movies;

/**
 * Data Transfer Object (DTO) for the Movies entity.
 * <p>
 * Used to transfer movie data between the client and the server.
 * Decouples the API layer from the Database layer.
 * </p>
 */
public class MoviesDTO {

    private String name;
    private Integer date; // Represents the Year
    private String tagline;
    private String description;
    private Integer minute;
    private Float rating;

    /** Default constructor. */
    public MoviesDTO() {}

    /** Full constructor. */
    public MoviesDTO(String name, Integer date, String tagline, String description, Integer minute, Float rating) {
        this.name = name;
        this.date = date;
        this.tagline = tagline;
        this.description = description;
        this.minute = minute;
        this.rating = rating;
    }

    // --- Getters and Setters ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDate() { return date; }
    public void setDate(Integer date) { this.date = date; }

    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getMinute() { return minute; }
    public void setMinute(Integer minute) { this.minute = minute; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}