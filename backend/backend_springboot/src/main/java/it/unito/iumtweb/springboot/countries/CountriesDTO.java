package it.unito.iumtweb.springboot.countries;

public class CountriesDTO {

    private Long movieId; // L'ID del film (come da istruzioni)
    private String country;

    // Costruttore vuoto (necessario per la deserializzazione JSON)
    public CountriesDTO() {}

    // Costruttore con tutti i campi
    public CountriesDTO(Long movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getter e Setter ---

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}