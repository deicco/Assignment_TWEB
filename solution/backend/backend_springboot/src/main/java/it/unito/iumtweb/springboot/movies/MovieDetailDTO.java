package it.unito.iumtweb.springboot.movies;

import it.unito.iumtweb.springboot.languages.*;
import it.unito.iumtweb.springboot.poster.Poster;
import it.unito.iumtweb.springboot.actors.Actors;

import java.util.List;

/**
 * Specific DTO for the "movie_detail.html" page
 * Group datas by cast and actors.
 */
public class MovieDetailDTO {
    private Movies movie;
    private List<Poster> posters;
    private List<Actors> cast;
    private List<Languages> languages;

    public MovieDetailDTO(Movies movie, List<Poster> posters, List<Actors> cast, List<Languages> languages) {
        this.movie = movie;
        this.posters = posters;
        this.cast = cast;
        this.languages = languages;
    }

    // Getters
    public Movies getMovie() { return movie; }
    public List<Poster> getPosters() { return posters; }
    public List<Actors> getCast() { return cast; }
    public List<Languages> getLanguages() { return languages; }
}