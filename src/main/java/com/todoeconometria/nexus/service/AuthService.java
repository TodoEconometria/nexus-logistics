// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar autenticacion JWT con registro y login.
// Capitulo 19C: "Seguridad con JWT"
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

    /**
     * Registra un nuevo usuario.
     * La password se almacena hasheada con BCrypt en el campo 'direccion'
     * del Cliente (simplificacion didactica - en produccion usar tabla separada).
     */
    public AuthResponseDTO registrar(AuthRequestDTO request) {
        // TODO: Implementar registro de usuario
        // 1. Verificar que el email no este ya registrado
        // 2. Crear nuevo Cliente con nombre y email
        // 3. Hashear la password con passwordEncoder.encode() y guardar en campo direccion
        // 4. Guardar el cliente
        // 5. Generar token JWT con jwtTokenProvider.generarToken(email)
        // 6. Retornar AuthResponseDTO con token y email
        throw new UnsupportedOperationException("TODO: Implementar registrar");
    }

    /**
     * Autentica un usuario existente.
     * Compara la password proporcionada con el hash almacenado.
     */
    public AuthResponseDTO login(AuthRequestDTO request) {
        // TODO: Implementar login
        // 1. Buscar cliente por email (si no existe, lanzar "Credenciales invalidas")
        // 2. Verificar password con passwordEncoder.matches(raw, encoded)
        //    La password hasheada esta en cliente.getDireccion()
        // 3. Si no coincide, lanzar "Credenciales invalidas"
        // 4. Generar token JWT y retornar AuthResponseDTO
        throw new UnsupportedOperationException("TODO: Implementar login");
    }
}
