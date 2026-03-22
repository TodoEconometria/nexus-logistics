// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.repository;

import com.todoeconometria.nexus.model.Vehiculo;
import com.todoeconometria.nexus.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByDisponibleTrue();

    List<Vehiculo> findByTipo(TipoVehiculo tipo);

    List<Vehiculo> findByTipoAndDisponibleTrue(TipoVehiculo tipo);

    Optional<Vehiculo> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}
