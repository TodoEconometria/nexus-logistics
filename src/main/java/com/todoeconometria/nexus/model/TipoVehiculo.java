// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.model;

/**
 * Tipos de vehiculos disponibles en la flota de Nexus.
 */
public enum TipoVehiculo {
    MOTO,       // Entregas urbanas rapidas, carga limitada
    FURGONETA,  // Entregas urbanas y periurbanas, carga media
    CAMION      // Entregas interurbanas, carga pesada
}
