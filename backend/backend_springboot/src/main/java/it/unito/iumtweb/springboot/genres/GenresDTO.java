package it.unito.iumtweb.springboot.genres;

public class GenresDTO {
    private Long id; // Movie ID
    private String genre;

    public GenresDTO() {}
    public GenresDTO(Long id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}