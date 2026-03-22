// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.service;

import com.todoeconometria.nexus.exception.BusinessRuleException;
import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.model.*;
import com.todoeconometria.nexus.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private RutaRepository rutaRepository;

    @InjectMocks
    private EnvioService envioService;

    private Cliente clienteTest;

    @BeforeEach
    void setUp() {
        clienteTest = new Cliente("Test Cliente", "test@example.com");
        clienteTest.setId(1L);
    }

    @Test
    void crearEnvio_clienteExiste_retornaEnvioConCosto() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteTest));
        when(envioRepository.save(any(Envio.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Envio resultado = envioService.crearEnvio(
            "Madrid", "Barcelona", new BigDecimal("5.0"), 1L);

        assertNotNull(resultado.getTrackingNumber());
        assertEquals(new BigDecimal("12.50"), resultado.getCosto());
        assertEquals(EstadoEnvio.REGISTRADO, resultado.getEstado());
        verify(envioRepository, times(1)).save(any(Envio.class));
    }

    @Test
    void crearEnvio_clienteNoExiste_lanzaExcepcion() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            envioService.crearEnvio("Madrid", "Barcelona", new BigDecimal("5.0"), 99L));
    }

    @Test
    void actualizarEstado_envioEntregado_noPuedeTransicionar() {
        Envio envioEntregado = new Envio(
            "Madrid", "Barcelona", new BigDecimal("2.0"), clienteTest);
        envioEntregado.setId(1L);
        envioEntregado.setEstado(EstadoEnvio.ENTREGADO);

        when(envioRepository.findByIdConRelaciones(1L))
            .thenReturn(Optional.of(envioEntregado));

        assertThrows(BusinessRuleException.class, () ->
            envioService.actualizarEstado(1L, EstadoEnvio.EN_TRANSITO));
    }
}
