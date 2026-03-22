// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class RutaDTO {
    private Long id;
    @NotBlank(message = "El nombre de la ruta es obligatorio")
    private String nombre;
    @NotBlank(message = "El origen de la ruta es obligatorio")
    private String origen;
    @NotBlank(message = "El destino de la ruta es obligatorio")
    private String destino;
    private Double distanciaKm;
    @NotNull(message = "La fecha programada es obligatoria")
    private LocalDate fechaProgramada;
    private boolean completada;
    private Long vehiculoId;
    private String vehiculoMatricula;
    private int totalEnvios;
    private List<String> trackingNumbers;

    public RutaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }
    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(LocalDate fechaProgramada) { this.fechaProgramada = fechaProgramada; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }
    public String getVehiculoMatricula() { return vehiculoMatricula; }
    public void setVehiculoMatricula(String vehiculoMatricula) { this.vehiculoMatricula = vehiculoMatricula; }
    public int getTotalEnvios() { return totalEnvios; }
    public void setTotalEnvios(int totalEnvios) { this.totalEnvios = totalEnvios; }
    public List<String> getTrackingNumbers() { return trackingNumbers; }
    public void setTrackingNumbers(List<String> trackingNumbers) { this.trackingNumbers = trackingNumbers; }
}
