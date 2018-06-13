package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Carrera_etapa.
 */
@Entity
@Table(name = "carrera_etapa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carrera_etapa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Carrera id_carrera;

    @ManyToOne(optional = false)
    @NotNull
    private Etapa id_etapa;

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

    public Carrera_etapa id_carrera(Carrera carrera) {
        this.id_carrera = carrera;
        return this;
    }

    public void setId_carrera(Carrera carrera) {
        this.id_carrera = carrera;
    }

    public Etapa getId_etapa() {
        return id_etapa;
    }

    public Carrera_etapa id_etapa(Etapa etapa) {
        this.id_etapa = etapa;
        return this;
    }

    public void setId_etapa(Etapa etapa) {
        this.id_etapa = etapa;
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
        Carrera_etapa carrera_etapa = (Carrera_etapa) o;
        if (carrera_etapa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carrera_etapa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Carrera_etapa{" +
            "id=" + getId() +
            "}";
    }
}
