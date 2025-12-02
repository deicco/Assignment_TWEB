package it.unito.iumtweb.springboot.themes;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ThemesPrimaryKey implements Serializable {
    private Long id; // Movie ID
    private String theme;

    public ThemesPrimaryKey() {}
    public ThemesPrimaryKey(Long id, String theme) {
        this.id = id;
        this.theme = theme;
    }
    // Standard Getters, Setters, Equals, HashCode
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemesPrimaryKey that = (ThemesPrimaryKey) o;
        return Objects.equals(id, that.id) && Objects.equals(theme, that.theme);
    }
    @Override
    public int hashCode() { return Objects.hash(id, theme); }
}