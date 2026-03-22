// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar CRUD de clientes con validaciones de negocio.
// Capitulo 19B: "Servicios de Negocio"
package com.todoeconometria.nexus.service;

import com.todoeconometria.nexus.exception.BusinessRuleException;
import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.model.Cliente;
import com.todoeconometria.nexus.repository.ClienteRepository;
import com.todoeconometria.nexus.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnvioRepository envioRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          EnvioRepository envioRepository) {
        this.clienteRepository = clienteRepository;
        this.envioRepository = envioRepository;
    }

    /**
     * Crea un nuevo cliente validando unicidad de email.
     */
    public Cliente crear(Cliente cliente) {
        // TODO: Implementar creacion de cliente
        // 1. Verificar que no exista otro cliente con el mismo email (existsByEmail)
        // 2. Crear nuevo objeto Cliente con nombre y email
        // 3. Asignar telefono y direccion
        // 4. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar crear");
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        // TODO: Buscar por ID, lanzar ResourceNotFoundException si no existe
        throw new UnsupportedOperationException("TODO: Implementar obtenerPorId");
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorEmail(String email) {
        // TODO: Buscar por email, lanzar ResourceNotFoundException si no existe
        throw new UnsupportedOperationException("TODO: Implementar obtenerPorEmail");
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Actualiza un cliente existente.
     * Si el email cambia, verificar que el nuevo email no este en uso.
     */
    public Cliente actualizar(Long id, Cliente datosActualizados) {
        // TODO: Implementar actualizacion con validacion de email unico
        // 1. Obtener el cliente existente
        // 2. Si el email cambio, verificar que el nuevo no este en uso
        // 3. Actualizar campos: nombre, email, telefono, direccion
        // 4. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar actualizar");
    }

    /**
     * Elimina un cliente. No se puede eliminar si tiene envios registrados.
     */
    public void eliminar(Long id) {
        // TODO: Implementar eliminacion con restriccion de integridad
        // Verificar que el cliente no tenga envios antes de eliminar
        // Pista: usar envioRepository.findByClienteId(id)
        throw new UnsupportedOperationException("TODO: Implementar eliminar");
    }
}
