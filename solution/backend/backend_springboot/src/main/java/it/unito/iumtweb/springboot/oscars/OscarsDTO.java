package it.unito.iumtweb.springboot.oscars;

/**
 * Data Transfer Object (DTO) for the Oscars entity.
 */
public class OscarsDTO {

    private Long id;
    private Integer movieId;
    private int yearFilm;
    private int yearCeremony;
    private int ceremony;
    private String category;
    private String name;
    private String film;
    private Boolean winner;

    public OscarsDTO() {}

    public OscarsDTO(Long id, Integer movieId, int yearFilm, int yearCeremony, int ceremony, String category, String name, String film, Boolean winner) {
        this.id = id;
        this.movieId = movieId;
        this.yearFilm = yearFilm;
        this.yearCeremony = yearCeremony;
        this.ceremony = ceremony;
        this.category = category;
        this.name = name;
        this.film = film;
        this.winner = winner;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public int getYearFilm() { return yearFilm; }
    public void setYearFilm(int yearFilm) { this.yearFilm = yearFilm; }

    public int getYearCeremony() { return yearCeremony; }
    public void setYearCeremony(int yearCeremony) { this.yearCeremony = yearCeremony; }

    public int getCeremony() { return ceremony; }
    public void setCeremony(int ceremony) { this.ceremony = ceremony; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFilm() { return film; }
    public void setFilm(String film) { this.film = film; }

    public Boolean getWinner() { return winner; }
    public void setWinner(Boolean winner) { this.winner = winner; }
}