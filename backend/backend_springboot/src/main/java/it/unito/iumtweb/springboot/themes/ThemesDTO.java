package it.unito.iumtweb.springboot.themes;

public class ThemesDTO {

    private Long id; // Movie ID
    private String theme;

    public ThemesDTO() {
    }

    public ThemesDTO(Long id, String theme) {
        this.id = id;
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}