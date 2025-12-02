package it.unito.iumtweb.springboot.studios;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudiosPrimaryKey implements Serializable {

    private Long movieId;
    private String studioName;

    public StudiosPrimaryKey() {}
    public StudiosPrimaryKey(Long movieId, String studioName) {
        this.movieId = movieId;
        this.studioName = studioName;
    }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudiosPrimaryKey that = (StudiosPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(studioName, that.studioName);
    }
    @Override
    public int hashCode() { return Objects.hash(movieId, studioName); }
}