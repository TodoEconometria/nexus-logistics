// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.service;

import com.todoeconometria.nexus.exception.BusinessRuleException;
import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.model.*;
import com.todoeconometria.nexus.repository.RutaRepository;
import com.todoeconometria.nexus.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RutaService {

    private final RutaRepository rutaRepository;
    private final VehiculoRepository vehiculoRepository;

    public RutaService(RutaRepository rutaRepository,
                       VehiculoRepository vehiculoRepository) {
        this.rutaRepository = rutaRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public Ruta crear(String nombre, String origen, String destino,
                      LocalDate fechaProgramada, Double distanciaKm, Long vehiculoId) {
        if (fechaProgramada.isBefore(LocalDate.now())) {
            throw new BusinessRuleException("La fecha programada no puede ser anterior a hoy");
        }
        Ruta ruta = new Ruta(nombre, origen, destino, fechaProgramada);
        ruta.setDistanciaKm(distanciaKm);
        if (vehiculoId != null) {
            asignarVehiculo(ruta, vehiculoId);
        }
        return rutaRepository.save(ruta);
    }

    @Transactional(readOnly = true)
    public Ruta obtenerPorId(Long id) {
        return rutaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ruta", "id", id));
    }

    @Transactional(readOnly = true)
    public Ruta obtenerConEnvios(Long id) {
        Ruta ruta = rutaRepository.findByIdConEnvios(id);
        if (ruta == null) {
            throw new ResourceNotFoundException("Ruta", "id", id);
        }
        return ruta;
    }

    @Transactional(readOnly = true)
    public List<Ruta> listarPendientes() {
        return rutaRepository.findByCompletadaFalseOrderByFechaProgramadaAsc();
    }

    @Transactional(readOnly = true)
    public List<Ruta> listarPorFecha(LocalDate fecha) {
        return rutaRepository.findByFechaProgramada(fecha);
    }

    @Transactional(readOnly = true)
    public List<Ruta> listarTodas() {
        return rutaRepository.findAll();
    }

    public Ruta completar(Long id) {
        Ruta ruta = obtenerConEnvios(id);
        if (ruta.isCompletada()) {
            throw new BusinessRuleException("La ruta '" + ruta.getNombre() + "' ya esta completada");
        }
        for (Envio envio : ruta.getEnvios()) {
            if (envio.getEstado() == EstadoEnvio.EN_TRANSITO) {
                envio.setEstado(EstadoEnvio.ENTREGADO);
                envio.setFechaEntrega(LocalDateTime.now());
            }
        }
        if (ruta.getVehiculo() != null) {
            ruta.getVehiculo().setDisponible(true);
            vehiculoRepository.save(ruta.getVehiculo());
        }
        ruta.setCompletada(true);
        return rutaRepository.save(ruta);
    }

    public Ruta asignarVehiculoARuta(Long rutaId, Long vehiculoId) {
        Ruta ruta = obtenerPorId(rutaId);
        if (ruta.isCompletada()) {
            throw new BusinessRuleException("No se puede modificar una ruta completada");
        }
        asignarVehiculo(ruta, vehiculoId);
        return rutaRepository.save(ruta);
    }

    private void asignarVehiculo(Ruta ruta, Long vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
            .orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "id", vehiculoId));
        if (!vehiculo.isDisponible()) {
            throw new BusinessRuleException(
                "El vehiculo con matricula '" + vehiculo.getMatricula() + "' no esta disponible");
        }
        if (ruta.getVehiculo() != null) {
            ruta.getVehiculo().setDisponible(true);
            vehiculoRepository.save(ruta.getVehiculo());
        }
        ruta.setVehiculo(vehiculo);
        vehiculo.setDisponible(false);
        vehiculoRepository.save(vehiculo);
    }
}
