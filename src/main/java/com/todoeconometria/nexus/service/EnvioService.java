// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.service;

import com.todoeconometria.nexus.exception.BusinessRuleException;
import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.model.*;
import com.todoeconometria.nexus.repository.EnvioRepository;
import com.todoeconometria.nexus.repository.ClienteRepository;
import com.todoeconometria.nexus.repository.RutaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class EnvioService {

    private static final BigDecimal TARIFA_POR_KG = new BigDecimal("2.50");

    private final EnvioRepository envioRepository;
    private final ClienteRepository clienteRepository;
    private final RutaRepository rutaRepository;

    public EnvioService(EnvioRepository envioRepository,
                        ClienteRepository clienteRepository,
                        RutaRepository rutaRepository) {
        this.envioRepository = envioRepository;
        this.clienteRepository = clienteRepository;
        this.rutaRepository = rutaRepository;
    }

    public Envio crearEnvio(String origen, String destino,
                            BigDecimal pesoKg, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));
        Envio envio = new Envio(origen, destino, pesoKg, cliente);
        envio.setCosto(pesoKg.multiply(TARIFA_POR_KG));
        return envioRepository.save(envio);
    }

    @Transactional(readOnly = true)
    public Envio obtenerPorId(Long id) {
        return envioRepository.findByIdConRelaciones(id)
            .orElseThrow(() -> new ResourceNotFoundException("Envio", "id", id));
    }

    @Transactional(readOnly = true)
    public Envio obtenerPorTracking(String trackingNumber) {
        return envioRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Envio", "tracking", trackingNumber));
    }

    @Transactional(readOnly = true)
    public Page<Envio> listarTodos(Pageable pageable) {
        return envioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Envio> buscarPorDestino(String destino, Pageable pageable) {
        return envioRepository.findByDestinoContainingIgnoreCase(destino, pageable);
    }

    public Envio actualizarEstado(Long id, EstadoEnvio nuevoEstado) {
        Envio envio = obtenerPorId(id);
        validarTransicionEstado(envio.getEstado(), nuevoEstado);
        envio.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoEnvio.ENTREGADO) {
            envio.setFechaEntrega(LocalDateTime.now());
        }
        return envioRepository.save(envio);
    }

    public Envio asignarARuta(Long envioId, Long rutaId) {
        Envio envio = obtenerPorId(envioId);
        Ruta ruta = rutaRepository.findById(rutaId)
            .orElseThrow(() -> new ResourceNotFoundException("Ruta", "id", rutaId));

        if (envio.getEstado() == EstadoEnvio.ENTREGADO ||
            envio.getEstado() == EstadoEnvio.DEVUELTO) {
            throw new BusinessRuleException(
                "No se puede asignar a ruta un envio en estado " + envio.getEstado());
        }
        if (ruta.isCompletada()) {
            throw new BusinessRuleException(
                "La ruta " + ruta.getNombre() + " ya esta completada");
        }

        ruta.asignarEnvio(envio);
        envio.setEstado(EstadoEnvio.EN_TRANSITO);
        return envioRepository.save(envio);
    }

    public void eliminar(Long id) {
        Envio envio = obtenerPorId(id);
        if (envio.getEstado() != EstadoEnvio.REGISTRADO) {
            throw new BusinessRuleException(
                "Solo se pueden eliminar envios en estado REGISTRADO. Estado actual: " + envio.getEstado());
        }
        envioRepository.deleteById(id);
    }

    private void validarTransicionEstado(EstadoEnvio actual, EstadoEnvio nuevo) {
        if (actual == EstadoEnvio.ENTREGADO || actual == EstadoEnvio.DEVUELTO) {
            throw new BusinessRuleException(
                "No se puede cambiar el estado de un envio " + actual + " a " + nuevo);
        }
        if (actual == EstadoEnvio.EN_TRANSITO && nuevo == EstadoEnvio.EN_ALMACEN) {
            throw new BusinessRuleException(
                "No se puede devolver un envio EN_TRANSITO a EN_ALMACEN");
        }
    }
}
