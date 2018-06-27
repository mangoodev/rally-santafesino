package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LocalidadCarrera.
 */
@Entity
@Table(name = "localidad_carrera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalidadCarrera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Carrera id_carrera;

    @ManyToOne(optional = false)
    @NotNull
    private Localidad id_localidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrera getId_carrera() {
        return id_carrera;
    }

    public LocalidadCarrera id_carrera(Carrera carrera) {
        this.id_carrera = carrera;
        return this;
    }

    public void setId_carrera(Carrera carrera) {
        this.id_carrera = carrera;
    }

    public Localidad getId_localidad() {
        return id_localidad;
    }

    public LocalidadCarrera id_localidad(Localidad localidad) {
        this.id_localidad = localidad;
        return this;
    }

    public void setId_localidad(Localidad localidad) {
        this.id_localidad = localidad;
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
        LocalidadCarrera localidadCarrera = (LocalidadCarrera) o;
        if (localidadCarrera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localidadCarrera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalidadCarrera{" +
            "id=" + getId() +
            "}";
    }
}
