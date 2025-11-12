package it.unito.iumtweb.springboot.movies;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "date")
    private LocalDateTime date;

    private String tagline;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private int minute;
    private Float rating;
    public Movies() {}

    public Movies(Long id, String name, LocalDateTime date, String tagline, String description, int minute, float rating) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.tagline = tagline;
        this.description = description;
        this.minute = minute;
        this.rating = rating;

    }

    // Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getTagline() {return tagline;}
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getDescription() {return description;}
    public void setDescription(String description) { this.description = description; }

    public int getMinute() {return minute;}
    public void setMinute(int minute) { this.minute = minute; }

    public float getRating() {return rating;}
    public void setRating(float rating) { this.rating = rating; }
}
