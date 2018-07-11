package com.rally.santafesino.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Carrera entity.
 */
public class CarreraDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private ZonedDateTime fecha;

    @NotNull
    private String sede;

    private ZonedDateTime inicioInscripcion;

    private ZonedDateTime finalInscripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public ZonedDateTime getInicioInscripcion() {
        return inicioInscripcion;
    }

    public void setInicioInscripcion(ZonedDateTime inicioInscripcion) {
        this.inicioInscripcion = inicioInscripcion;
    }

    public ZonedDateTime getFinalInscripcion() {
        return finalInscripcion;
    }

    public void setFinalInscripcion(ZonedDateTime finalInscripcion) {
        this.finalInscripcion = finalInscripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarreraDTO carreraDTO = (CarreraDTO) o;
        if(carreraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carreraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarreraDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", sede='" + getSede() + "'" +
            ", inicioInscripcion='" + getInicioInscripcion() + "'" +
            ", finalInscripcion='" + getFinalInscripcion() + "'" +
            "}";
    }
}
