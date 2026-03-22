// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar gestion de rutas con asignacion de vehiculos.
// Capitulo 19B: "Servicios de Negocio"
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

    /**
     * Crea una nueva ruta. La fecha programada no puede ser anterior a hoy.
     * Opcionalmente asigna un vehiculo.
     */
    public Ruta crear(String nombre, String origen, String destino,
                      LocalDate fechaProgramada, Double distanciaKm, Long vehiculoId) {
        // TODO: Implementar creacion de ruta
        // 1. Validar que fechaProgramada no sea anterior a hoy
        // 2. Crear Ruta con constructor (nombre, origen, destino, fechaProgramada)
        // 3. Asignar distanciaKm
        // 4. Si vehiculoId no es null, asignar vehiculo (ver metodo privado asignarVehiculo)
        // 5. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar crear");
    }

    @Transactional(readOnly = true)
    public Ruta obtenerPorId(Long id) {
        return rutaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ruta", "id", id));
    }

    @Transactional(readOnly = true)
    public Ruta obtenerConEnvios(Long id) {
        // TODO: Usar findByIdConEnvios del repositorio (JOIN FETCH)
        // Lanzar ResourceNotFoundException si retorna null
        throw new UnsupportedOperationException("TODO: Implementar obtenerConEnvios");
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

    /**
     * Completa una ruta: marca todos los envios EN_TRANSITO como ENTREGADOS,
     * libera el vehiculo asignado, y marca la ruta como completada.
     * Este es el metodo mas complejo del servicio - involucra multiples entidades.
     */
    public Ruta completar(Long id) {
        // TODO: Implementar completar ruta (operacion transaccional compleja)
        // 1. Obtener la ruta CON sus envios cargados (obtenerConEnvios)
        // 2. Validar que no este ya completada
        // 3. Iterar envios: si estado == EN_TRANSITO, cambiar a ENTREGADO + asignar fechaEntrega
        // 4. Si tiene vehiculo asignado: marcarlo como disponible y guardarlo
        // 5. Marcar ruta como completada
        // 6. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar completar");
    }

    /**
     * Asigna o reasigna un vehiculo a una ruta existente.
     */
    public Ruta asignarVehiculoARuta(Long rutaId, Long vehiculoId) {
        // TODO: Implementar asignacion de vehiculo a ruta
        // 1. Obtener la ruta, validar que no este completada
        // 2. Llamar al metodo privado asignarVehiculo
        // 3. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar asignarVehiculoARuta");
    }

    /**
     * Asigna un vehiculo a una ruta.
     * Si la ruta ya tenia vehiculo, libera el anterior.
     * Marca el nuevo vehiculo como no disponible.
     */
    private void asignarVehiculo(Ruta ruta, Long vehiculoId) {
        // TODO: Implementar asignacion de vehiculo
        // 1. Buscar vehiculo por ID
        // 2. Validar que este disponible
        // 3. Si la ruta ya tenia vehiculo, liberar el anterior (disponible = true)
        // 4. Asignar nuevo vehiculo a ruta, marcar como no disponible
        throw new UnsupportedOperationException("TODO: Implementar asignarVehiculo");
    }
}
