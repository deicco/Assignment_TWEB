package it.unito.iumtweb.springboot.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// La PK è di tipo Long, come definito in Crew.java
@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {

    // Metodo per trovare il crew in base al ruolo, se necessario in futuro
    // List<Crew> findByRole(String role);
}