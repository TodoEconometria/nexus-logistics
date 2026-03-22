// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.service;

import com.todoeconometria.nexus.dto.AuthRequestDTO;
import com.todoeconometria.nexus.dto.AuthResponseDTO;
import com.todoeconometria.nexus.exception.BusinessRuleException;
import com.todoeconometria.nexus.model.Cliente;
import com.todoeconometria.nexus.repository.ClienteRepository;
import com.todoeconometria.nexus.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(ClienteRepository clienteRepository,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO registrar(AuthRequestDTO request) {
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException(
                "Ya existe un usuario con el email: " + request.getEmail());
        }
        Cliente cliente = new Cliente(
            request.getNombre() != null ? request.getNombre() : "Usuario",
            request.getEmail()
        );
        // Almacenar password hasheada en campo direccion (simplificacion didactica)
        cliente.setDireccion(passwordEncoder.encode(request.getPassword()));
        clienteRepository.save(cliente);

        String token = jwtTokenProvider.generarToken(request.getEmail());
        return new AuthResponseDTO(token, request.getEmail());
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessRuleException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.getPassword(), cliente.getDireccion())) {
            throw new BusinessRuleException("Credenciales invalidas");
        }

        String token = jwtTokenProvider.generarToken(request.getEmail());
        return new AuthResponseDTO(token, request.getEmail());
    }
}
