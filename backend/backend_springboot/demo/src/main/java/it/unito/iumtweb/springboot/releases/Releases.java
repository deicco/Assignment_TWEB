package it.unito.iumtweb.springboot.releases;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "releases")
public class Releases {

    @Id
    private Long id;
    private String country;
    private Date date;
    private String type;
    private Float rating;

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getCountry() {return country;}
    public void setCountry(String country) { this.country = country; }

    public Date getDate() {return date;}
    public void setDate(Date date) { this.date = date; }

    public String getType() {return type;}
    public void setType(String type) { this.type = type; }

    public float getRating() {return rating;}
    public void setRating(float rating) { this.rating = rating; }
}
