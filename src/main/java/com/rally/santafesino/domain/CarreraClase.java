package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CarreraClase.
 */
@Entity
@Table(name = "carrera_clase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CarreraClase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Carrera carrera;

    @ManyToOne
    private Clase clase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public CarreraClase carrera(Carrera carrera) {
        this.carrera = carrera;
        return this;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Clase getClase() {
        return clase;
    }

    public CarreraClase clase(Clase clase) {
        this.clase = clase;
        return this;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
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
        CarreraClase carreraClase = (CarreraClase) o;
        if (carreraClase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carreraClase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarreraClase{" +
            "id=" + getId() +
            "}";
    }
}
