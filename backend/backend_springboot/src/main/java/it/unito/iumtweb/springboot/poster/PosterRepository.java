package it.unito.iumtweb.springboot.poster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link Poster} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Allows pagination to safely browse the 940,000+ entries.
 * </p>
 */
@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
    // Standard JpaRepository methods (findAll, findById) are sufficient.
    // Pagination is supported by default in JpaRepository methods.
}