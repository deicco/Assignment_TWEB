package it.unito.iumtweb.springboot.poster;

/**
 * Data Transfer Object (DTO) for the Poster entity.
 * <p>
 * Used to transfer poster link data.
 * </p>
 */
public class PosterDTO {

    private String link;

    public PosterDTO() {}

    public PosterDTO(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}