package it.unito.iumtweb.springboot.countries;

import it.unito.iumtweb.springboot.movies.Movies;
import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    private long id;

    @ManyToOne
    @MapsId("id")
   @JoinColumn(name = "id", nullable = false)
    private Movies movies;

    public Country() {}

    public Country(Movies movie, String country) {
        this.id = new CountryPrimaryKey(movies.getId(), country);
        this.movies = movie;
    }

    public CountryPrimaryKey getId() {return id;}
    public void setId(CountryPrimaryKey id) {this.id = id;}

    public Movies getMovies() {return movies;}
    public void setMovies(Movies movies) {this.movies = movies;}

}