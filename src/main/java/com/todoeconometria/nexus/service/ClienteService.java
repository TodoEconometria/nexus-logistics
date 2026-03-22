// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
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

    public Cliente crear(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new BusinessRuleException(
                "Ya existe un cliente con el email: " + cliente.getEmail());
        }
        Cliente nuevo = new Cliente(cliente.getNombre(), cliente.getEmail());
        nuevo.setTelefono(cliente.getTelefono());
        nuevo.setDireccion(cliente.getDireccion());
        return clienteRepository.save(nuevo);
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorEmail(String email) {
        return clienteRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "email", email));
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente actualizar(Long id, Cliente datosActualizados) {
        Cliente cliente = obtenerPorId(id);
        if (!cliente.getEmail().equals(datosActualizados.getEmail())) {
            if (clienteRepository.existsByEmail(datosActualizados.getEmail())) {
                throw new BusinessRuleException(
                    "Ya existe un cliente con el email: " + datosActualizados.getEmail());
            }
        }
        cliente.setNombre(datosActualizados.getNombre());
        cliente.setEmail(datosActualizados.getEmail());
        cliente.setTelefono(datosActualizados.getTelefono());
        cliente.setDireccion(datosActualizados.getDireccion());
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        if (!envioRepository.findByClienteId(id).isEmpty()) {
            throw new BusinessRuleException(
                "No se puede eliminar el cliente '" + cliente.getNombre()
                + "' porque tiene envios registrados");
        }
        clienteRepository.deleteById(id);
    }
}
