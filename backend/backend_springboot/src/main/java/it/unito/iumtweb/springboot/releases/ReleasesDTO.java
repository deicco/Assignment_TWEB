package it.unito.iumtweb.springboot.releases;

import java.util.Date;

public class ReleasesDTO {

    // Campi Chiave
    private Long id; // ID del Film
    private String country;

    // Campi Non-Chiave
    private Date date;
    private String type;
    private Float rating;

    public ReleasesDTO() {}

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}