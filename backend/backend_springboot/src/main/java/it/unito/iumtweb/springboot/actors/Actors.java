package it.unito.iumtweb.springboot.actors;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "actors")
public class Actors {

    @EmbeddedId
    private ActorsPrimaryKey id; // Questo oggetto contiene già 'id' (del film) e 'name' (dell'attore)

    // ERRORE ERA QUI: Rimuovi 'private String name;' e 'private Long id;' se presenti.
    // L'unico campo che rimane fuori dalla chiave è il ruolo.
    private String role;

    public Actors() {}

    // --- Getter e Setter per la Chiave ---
    public ActorsPrimaryKey getId() {
        return id;
    }

    public void setId(ActorsPrimaryKey id) {
        this.id = id;
    }

    // --- Getter e Setter per il Ruolo ---
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // --- Metodi Helper opzionali (per leggere i dati senza passare da .getId()) ---
    // Nota: Questi NON devono avere annotazioni @Column o @Id
    public String getName() {
        return id != null ? id.getName() : null;
    }

    public Long getMovieId() {
        return id != null ? id.getId() : null;
    }
}