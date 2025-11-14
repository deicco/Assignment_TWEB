package it.unito.iumtweb.springboot.languages;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "languages")
public class Languages {

    @Id
    private int id;

    private String type;
    private String language;

    public int getId() {return id;}
    public void setId(int id) { this.id = id; }

    public String getType() {return type;}
    public void setType(String type) { this.type = type; }

    public String getLanguage() {return language;}
    public void setLanguage(String language) { this.language = language; }
}
