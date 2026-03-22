// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar CRUD REST para almacenes.
// Capitulo 19B: "Controladores REST Adicionales"
package com.todoeconometria.nexus.controller;

import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.model.Almacen;
import com.todoeconometria.nexus.repository.AlmacenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@Tag(name = "Almacenes", description = "Gestion de almacenes de distribucion")
public class AlmacenController {

    private final AlmacenRepository almacenRepository;

    public AlmacenController(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    // TODO: Implementar GET /api/almacenes (listar todos)

    // TODO: Implementar GET /api/almacenes/{id} (obtener por ID)

    // TODO: Implementar GET /api/almacenes/ciudad/{ciudad} (filtrar por ciudad)

    // TODO: Implementar GET /api/almacenes/disponibles (listar con espacio)
    // Pista: usar findAlmacenesConEspacio() del repositorio (consulta @Query)

    // TODO: Implementar POST /api/almacenes (crear almacen)
    // Usar @Valid @RequestBody, asignar id = null

    // TODO: Implementar PUT /api/almacenes/{id} (actualizar almacen)
    // Actualizar: nombre, ciudad, direccion, capacidadMaxima

    // TODO: Implementar DELETE /api/almacenes/{id} (eliminar almacen)
}
