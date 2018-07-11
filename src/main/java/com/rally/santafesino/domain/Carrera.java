package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Carrera.
 */
@Entity
@Table(name = "carrera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private ZonedDateTime fecha;

    @NotNull
    @Column(name = "sede", nullable = false)
    private String sede;

    @Column(name = "inicio_inscripcion")
    private ZonedDateTime inicioInscripcion;

    @Column(name = "final_inscripcion")
    private ZonedDateTime finalInscripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Carrera nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Carrera descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Carrera fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getSede() {
        return sede;
    }

    public Carrera sede(String sede) {
        this.sede = sede;
        return this;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public ZonedDateTime getInicioInscripcion() {
        return inicioInscripcion;
    }

    public Carrera inicioInscripcion(ZonedDateTime inicioInscripcion) {
        this.inicioInscripcion = inicioInscripcion;
        return this;
    }

    public void setInicioInscripcion(ZonedDateTime inicioInscripcion) {
        this.inicioInscripcion = inicioInscripcion;
    }

    public ZonedDateTime getFinalInscripcion() {
        return finalInscripcion;
    }

    public Carrera finalInscripcion(ZonedDateTime finalInscripcion) {
        this.finalInscripcion = finalInscripcion;
        return this;
    }

    public void setFinalInscripcion(ZonedDateTime finalInscripcion) {
        this.finalInscripcion = finalInscripcion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carrera carrera = (Carrera) o;
        if (carrera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carrera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Carrera{" +
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
