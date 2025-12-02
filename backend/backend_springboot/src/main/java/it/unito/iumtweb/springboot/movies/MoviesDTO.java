package it.unito.iumtweb.springboot.movies;

import java.time.LocalDateTime;

public class MoviesDTO {

    private String name;
    private LocalDateTime date;
    private String tagline;
    private String description;
    private int minute;
    private Float rating;

    public MoviesDTO() {}

    // Costruttore completo (opzionale ma utile)
    public MoviesDTO(String name, LocalDateTime date, String tagline, String description, int minute, Float rating) {
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

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}