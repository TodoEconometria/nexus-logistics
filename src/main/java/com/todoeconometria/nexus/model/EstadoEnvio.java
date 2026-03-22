// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.model;

/**
 * Estados posibles de un envio en el sistema Nexus.
 *
 * Ciclo de vida:
 * REGISTRADO -> EN_ALMACEN -> EN_TRANSITO -> ENTREGADO
 *                                         -> DEVUELTO
 *
 * Las transiciones son unidireccionales: un envio ENTREGADO
 * no puede volver a EN_TRANSITO. Esta restriccion se valida
 * en EnvioService, no en el enum.
 */
public enum EstadoEnvio {
    REGISTRADO,    // Recien creado en el sistema, sin procesar
    EN_ALMACEN,    // Recibido en un almacen, esperando asignacion a ruta
    EN_TRANSITO,   // Asignado a una ruta y en camino al destino
    ENTREGADO,     // Entregado exitosamente al destinatario
    DEVUELTO       // No se pudo entregar, devuelto al remitente
}
