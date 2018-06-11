package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Auto_carrera.
 */
@Entity
@Table(name = "auto_carrera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auto_carrera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creation_date;

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

    public ZonedDateTime getCreation_date() {
        return creation_date;
    }

    public Auto_carrera creation_date(ZonedDateTime creation_date) {
        this.creation_date = creation_date;
        return this;
    }

    public void setCreation_date(ZonedDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public Auto getAuto() {
        return auto;
    }

    public Auto_carrera auto(Auto auto) {
        this.auto = auto;
        return this;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public Auto_carrera carrera(Carrera carrera) {
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
        Auto_carrera auto_carrera = (Auto_carrera) o;
        if (auto_carrera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auto_carrera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auto_carrera{" +
            "id=" + getId() +
            ", creation_date='" + getCreation_date() + "'" +
            "}";
    }
}
