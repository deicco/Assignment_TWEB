package it.unito.iumtweb.springboot.crew;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "crew")
public class Crew {

    @EmbeddedId
    private CrewPrimaryKey id; // Usa la chiave composta definita in CrewPrimaryKey

    // Il campo 'role' non fa parte della chiave
    private String role;

    public Crew() {}

    public Crew(CrewPrimaryKey id, String role) {
        this.id = id;
        this.role = role;
    }

    // --- Getter e Setter ---

    public CrewPrimaryKey getId() {
        return id;
    }
    public void setId(CrewPrimaryKey id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}