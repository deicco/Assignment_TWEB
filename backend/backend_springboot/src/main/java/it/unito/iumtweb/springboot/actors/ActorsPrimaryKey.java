package it.unito.iumtweb.springboot.actors;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ActorsPrimaryKey implements Serializable {

    private Long id; // ID del Film
    private String name; // Nome dell'Attore

    public ActorsPrimaryKey() {}
    public ActorsPrimaryKey(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActorsPrimaryKey(String role, String name) {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorsPrimaryKey that = (ActorsPrimaryKey) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}