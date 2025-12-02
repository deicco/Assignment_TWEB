package it.unito.iumtweb.springboot.languages;

public class LanguagesDTO {
    private int id;
    private String language;
    private String type;

    // Costruttori, Getters e Setters
    public LanguagesDTO() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}