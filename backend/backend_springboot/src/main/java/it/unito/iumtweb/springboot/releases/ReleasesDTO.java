package it.unito.iumtweb.springboot.releases;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for the Releases entity.
 * <p>
 * Transfers release information between client and server.
 * </p>
 */
public class ReleasesDTO {

    private Long movieId; // Renamed from 'id' for clarity
    private String country;
    private Date date;
    private String type;
    private Float rating;

    public ReleasesDTO() {}

    // --- Getters and Setters ---

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}