package it.unito.iumtweb.springboot.poster;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "posters")
public class Poster {

    @Id
    private Long id;
    @Lob
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getLink() {return link;}
    public void setLink(String link) { this.link = link; }
}
