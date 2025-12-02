package it.unito.iumtweb.springboot.releases;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "releases")
public class Releases {

    @EmbeddedId // Usa la chiave composta
    private ReleasesPrimaryKey id;

    // I campi 'id' e 'country' sono ora nella PK.
    private Date date;
    private String type;
    private Float rating; // Usare Float per consistenza con i getter/setter di Movies

    public Releases() {}

    // --- Getters e Setters ---

    public ReleasesPrimaryKey getId() {return id;}
    public void setId(ReleasesPrimaryKey id) { this.id = id; }

    // I metodi per id e country ora usano la PK
    public Long getMovieId() { return id.getId(); }
    public String getCountry() { return id.getCountry(); }

    // Non servono setter per MovieId e Country, si setta tramite PK

    public Date getDate() {return date;}
    public void setDate(Date date) { this.date = date; }

    public String getType() {return type;}
    public void setType(String type) { this.type = type; }

    public Float getRating() {return rating;}
    public void setRating(Float rating) { this.rating = rating; }
}