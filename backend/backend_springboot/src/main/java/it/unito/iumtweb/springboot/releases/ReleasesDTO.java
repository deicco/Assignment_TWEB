package it.unito.iumtweb.springboot.releases;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for the Releases entity.
 */
public class ReleasesDTO {

    private Long id;
    private Integer movieId;
    private String country;
    private Date date;
    private String type;
    private Float rating;

    public ReleasesDTO() {}

    public ReleasesDTO(Long id, Integer movieId, String country, Date date, String type, Float rating) {
        this.id = id;
        this.movieId = movieId;
        this.country = country;
        this.date = date;
        this.type = type;
        this.rating = rating;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}