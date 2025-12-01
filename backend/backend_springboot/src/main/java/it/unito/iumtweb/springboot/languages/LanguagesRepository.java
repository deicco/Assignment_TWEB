package it.unito.iumtweb.springboot.languages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Integer> {

    // Potrebbe servire per cercare per lingua o per tipo
    // List<Languages> findByLanguage(String language);
}