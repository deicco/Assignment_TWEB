package it.unito.iumtweb.springboot.actors;
import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "actors")
public class Actors {

    @Id
    private Long id;
    private String name;
    private String role;

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public String getRole() {return role;}
    public void setRole(String role) { this.role = role; }
}
