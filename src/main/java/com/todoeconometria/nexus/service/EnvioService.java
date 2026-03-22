// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar la logica de negocio siguiendo las instrucciones del libro.
// Capitulo 19A: "Arquitectura de Servicios" y "Maquina de Estados del Envio"
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

    // TODO: Definir la constante TARIFA_POR_KG como BigDecimal (valor: 2.50 EUR/kg)
    // Pista: usar new BigDecimal("2.50") para evitar problemas de precision con double

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

    /**
     * Crea un nuevo envio asignado a un cliente existente.
     * El costo se calcula automaticamente: pesoKg * TARIFA_POR_KG.
     * El tracking number se genera en el constructor de Envio.
     */
    public Envio crearEnvio(String origen, String destino,
                            BigDecimal pesoKg, Long clienteId) {
        // TODO: Implementar creacion de envio
        // 1. Buscar el cliente por ID (lanzar ResourceNotFoundException si no existe)
        // 2. Crear un nuevo Envio con el constructor (origen, destino, pesoKg, cliente)
        // 3. Calcular el costo: pesoKg.multiply(TARIFA_POR_KG)
        // 4. Guardar y retornar el envio
        throw new UnsupportedOperationException("TODO: Implementar crearEnvio");
    }

    @Transactional(readOnly = true)
    public Envio obtenerPorId(Long id) {
        // TODO: Usar findByIdConRelaciones del repositorio (JOIN FETCH)
        // Lanzar ResourceNotFoundException si no existe
        throw new UnsupportedOperationException("TODO: Implementar obtenerPorId");
    }

    @Transactional(readOnly = true)
    public Envio obtenerPorTracking(String trackingNumber) {
        // TODO: Usar findByTrackingNumber del repositorio
        // Lanzar ResourceNotFoundException si no existe
        throw new UnsupportedOperationException("TODO: Implementar obtenerPorTracking");
    }

    @Transactional(readOnly = true)
    public Page<Envio> listarTodos(Pageable pageable) {
        return envioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Envio> buscarPorDestino(String destino, Pageable pageable) {
        return envioRepository.findByDestinoContainingIgnoreCase(destino, pageable);
    }

    /**
     * Actualiza el estado de un envio respetando la maquina de estados.
     * Transiciones prohibidas:
     * - ENTREGADO o DEVUELTO no pueden cambiar a ningun otro estado
     * - EN_TRANSITO no puede retroceder a EN_ALMACEN
     * Si el nuevo estado es ENTREGADO, asignar fechaEntrega = ahora.
     */
    public Envio actualizarEstado(Long id, EstadoEnvio nuevoEstado) {
        // TODO: Implementar actualizacion de estado con validaciones
        // 1. Obtener el envio por ID
        // 2. Validar la transicion de estado (ver metodo validarTransicionEstado)
        // 3. Asignar el nuevo estado
        // 4. Si es ENTREGADO, asignar fechaEntrega
        // 5. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar actualizarEstado");
    }

    /**
     * Asigna un envio a una ruta existente.
     * Restricciones:
     * - No asignar envios ENTREGADOS o DEVUELTOS
     * - No asignar a rutas ya completadas
     * El envio pasa automaticamente a estado EN_TRANSITO.
     */
    public Envio asignarARuta(Long envioId, Long rutaId) {
        // TODO: Implementar asignacion a ruta con validaciones de negocio
        // 1. Obtener envio y ruta
        // 2. Validar que el envio no este ENTREGADO ni DEVUELTO
        // 3. Validar que la ruta no este completada
        // 4. Asignar envio a ruta: ruta.asignarEnvio(envio)
        // 5. Cambiar estado del envio a EN_TRANSITO
        // 6. Guardar y retornar
        throw new UnsupportedOperationException("TODO: Implementar asignarARuta");
    }

    /**
     * Elimina un envio. Solo se permite eliminar envios en estado REGISTRADO.
     */
    public void eliminar(Long id) {
        // TODO: Implementar eliminacion con restriccion de estado
        // Solo se pueden eliminar envios en estado REGISTRADO
        throw new UnsupportedOperationException("TODO: Implementar eliminar");
    }

    /**
     * Valida que la transicion de estado sea permitida.
     * Reglas de la maquina de estados:
     * - Estados terminales (ENTREGADO, DEVUELTO) no pueden transicionar
     * - EN_TRANSITO no puede retroceder a EN_ALMACEN
     */
    private void validarTransicionEstado(EstadoEnvio actual, EstadoEnvio nuevo) {
        // TODO: Implementar validacion de transiciones
        // Ver diagrama de estados en el Capitulo 19A del libro
        throw new UnsupportedOperationException("TODO: Implementar validarTransicionEstado");
    }
}
