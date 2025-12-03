package it.unito.iumtweb.springboot.oscars;

/**
 * Data Transfer Object (DTO) for the Oscars entity.
 * <p>
 * Transfers data about awards and nominations.
 * </p>
 */
public class OscarsDTO {

    // Key fields
    private int year_film;
    private String category;
    private String film;
    private String name;

    // Data fields
    private int year_ceremony;
    private int ceremony;
    private Boolean winner;

    public OscarsDTO() {}

    // --- Getters and Setters ---

    public int getYear_film() { return year_film; }
    public void setYear_film(int year_film) { this.year_film = year_film; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFilm() { return film; }
    public void setFilm(String film) { this.film = film; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear_ceremony() { return year_ceremony; }
    public void setYear_ceremony(int year_ceremony) { this.year_ceremony = year_ceremony; }

    public int getCeremony() { return ceremony; }
    public void setCeremony(int ceremony) { this.ceremony = ceremony; }

    public Boolean getWinner() { return winner; }
    public void setWinner(Boolean winner) { this.winner = winner; }
}