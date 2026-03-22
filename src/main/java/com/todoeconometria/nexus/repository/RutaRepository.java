// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.repository;

import com.todoeconometria.nexus.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

    List<Ruta> findByFechaProgramada(LocalDate fecha);

    List<Ruta> findByCompletadaFalseOrderByFechaProgramadaAsc();

    @Query("SELECT r FROM Ruta r LEFT JOIN FETCH r.envios WHERE r.id = :id")
    Ruta findByIdConEnvios(Long id);
}
