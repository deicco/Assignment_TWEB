package it.unito.iumtweb.springboot.studios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudiosRepository extends JpaRepository<Studios, Long> {

    // Metodo per trovare uno studio per nome
    Studios findByStudio(String studio);
}