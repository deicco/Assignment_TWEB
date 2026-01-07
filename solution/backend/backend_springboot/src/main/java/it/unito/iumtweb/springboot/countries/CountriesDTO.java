package it.unito.iumtweb.springboot.countries;

/**
 * Data Transfer Object (DTO) for the Country entity.
 * <p>
 * Used to transfer country data between the client and the server.
 * </p>
 */
public class CountriesDTO {

    private Long id;         // Unique ID (optional)
    private Integer movieId; // ID of the movie (Integer)
    private String country;  // Name of the country

    /**
     * Default no-argument constructor.
     */
    public CountriesDTO() {}

    /**
     * Full constructor.
     */
    public CountriesDTO(Long id, Integer movieId, String country) {
        this.id = id;
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}