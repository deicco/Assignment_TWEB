package it.unito.iumtweb.springboot.oscars;
import jakarta.persistence.*;

@Entity
@Table(name = "oscars")
public class Oscars {

    @EmbeddedId // Usa la chiave composta
    private OscarsPrimaryKey id;

    private int year_ceremony;
    private int ceremony;
    private String name;
    private Boolean winner; // Usare Boolean per consistenza con il tuo file originale

    public Oscars() {}

    // --- Getters e Setters ---

    public OscarsPrimaryKey getId() { return id; }
    public void setId(OscarsPrimaryKey id) { this.id = id; }

    public int getYear_film() {
        return id.getYear_film(); // Recuperato dalla PK
    }
    // non serve setYear_film, si setta tramite PK

    public int getYear_ceremony() {
        return year_ceremony;
    }
    public void setYear_ceremony(int year_ceremony) {
        this.year_ceremony = year_ceremony;
    }

    public int getCeremony() {
        return ceremony;
    }
    public void setCeremony(int ceremony) {
        this.ceremony = ceremony;
    }

    public String getCategory() {
        return id.getCategory(); // Recuperato dalla PK
    }
    // non serve setCategory, si setta tramite PK

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFilm() {
        return id.getFilm(); // Recuperato dalla PK
    }
    // non serve setFilm, si setta tramite PK

    // Metodo getIsWinner modificato per consistenza
    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}