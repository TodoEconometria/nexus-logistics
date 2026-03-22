// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar endpoints de autenticacion JWT.
// Capitulo 19C: "Seguridad con JWT"
package com.todoeconometria.nexus.controller;

import com.todoeconometria.nexus.dto.AuthRequestDTO;
import com.todoeconometria.nexus.dto.AuthResponseDTO;
import com.todoeconometria.nexus.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Login y registro de usuarios")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // TODO: Implementar POST /api/auth/login
    // Recibir @Valid @RequestBody AuthRequestDTO
    // Delegar a authService.login()
    // Retornar ResponseEntity con el AuthResponseDTO (contiene token JWT)

    // TODO: Implementar POST /api/auth/register
    // Recibir @Valid @RequestBody AuthRequestDTO
    // Delegar a authService.registrar()
    // Retornar HttpStatus.CREATED con AuthResponseDTO
}
