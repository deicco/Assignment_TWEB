package it.unito.iumtweb.springboot.studios;
import jakarta.persistence.*;

@Entity
@Table(name = "studios")
public class Studios {

    @Id
    private Long id;
    private String studio;

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getStudio() {return studio;}
    public void setStudio(String studio) { this.studio = studio; }
}
