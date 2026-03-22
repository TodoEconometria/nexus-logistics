// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.repository;

import com.todoeconometria.nexus.model.Envio;
import com.todoeconometria.nexus.model.EstadoEnvio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

    Optional<Envio> findByTrackingNumber(String trackingNumber);

    Page<Envio> findByEstado(EstadoEnvio estado, Pageable pageable);

    Page<Envio> findByDestinoContainingIgnoreCase(String destino, Pageable pageable);

    List<Envio> findByClienteId(Long clienteId);

    @Query("SELECT e.estado, COUNT(e) FROM Envio e " +
           "WHERE e.cliente.id = :clienteId " +
           "GROUP BY e.estado")
    List<Object[]> contarPorEstadoYCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT e FROM Envio e " +
           "LEFT JOIN FETCH e.cliente " +
           "LEFT JOIN FETCH e.ruta " +
           "WHERE e.id = :id")
    Optional<Envio> findByIdConRelaciones(@Param("id") Long id);
}
