package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AutoCarrera.
 */
@Entity
@Table(name = "auto_carrera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutoCarrera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Auto auto;

    @ManyToOne(optional = false)
    @NotNull
    private Carrera carrera;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Auto getAuto() {
        return auto;
    }

    public AutoCarrera auto(Auto auto) {
        this.auto = auto;
        return this;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public AutoCarrera carrera(Carrera carrera) {
        this.carrera = carrera;
        return this;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
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
        AutoCarrera autoCarrera = (AutoCarrera) o;
        if (autoCarrera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autoCarrera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutoCarrera{" +
            "id=" + getId() +
            "}";
    }
}
