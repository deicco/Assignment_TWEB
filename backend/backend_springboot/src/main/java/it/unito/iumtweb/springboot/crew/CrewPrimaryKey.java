package it.unito.iumtweb.springboot.crew;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CrewPrimaryKey implements Serializable {

    private Long movieId;
    private String crewName;

    public CrewPrimaryKey() {

    }

    public CrewPrimaryKey(Long movieId, String crewName) {
        this.movieId = movieId;
        this.crewName = crewName;
    }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getCrewName() { return crewName; }
    public void setCrewName(String crewName) { this.crewName = crewName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewPrimaryKey that = (CrewPrimaryKey) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(crewName, that.crewName);
    }
    @Override
    public int hashCode() { return Objects.hash(movieId, crewName); }
}