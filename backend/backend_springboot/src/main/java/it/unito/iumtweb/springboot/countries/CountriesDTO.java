package it.unito.iumtweb.springboot.countries;

/**
 * Data Transfer Object (DTO) for the Country entity.
 * <p>
 * Used to transfer country data between the client (Main Express Server) and this Spring Boot application.
 * This pattern decouples the internal database structure from the external API.
 * </p>
 */
public class CountriesDTO {

    /** The ID of the film associated with the country. */
    private Long movieId;

    /** The name of the country. */
    private String country;

    /**
     * Default no-argument constructor.
     */
    public CountriesDTO() {}

    /**
     * Full constructor for creating a DTO instance.
     *
     * @param movieId The ID of the film.
     * @param country The name of the country.
     */
    public CountriesDTO(Long movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    // --- Getters and Setters ---

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