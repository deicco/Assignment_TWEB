package it.unito.iumtweb.springboot.actors;
import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "crew")
public class Actors {

    @Id
    private Long id;
    private String role;
    private String name;

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getRole() {return role;}
    public void setRole(String role) { this.role = role; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

}
