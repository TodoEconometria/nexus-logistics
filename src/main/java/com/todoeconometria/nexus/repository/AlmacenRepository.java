// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.repository;

import com.todoeconometria.nexus.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

    List<Almacen> findByCiudad(String ciudad);

    Optional<Almacen> findByNombre(String nombre);

    @Query("SELECT a FROM Almacen a WHERE SIZE(a.envios) < a.capacidadMaxima")
    List<Almacen> findAlmacenesConEspacio();
}
