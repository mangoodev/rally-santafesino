package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pruebas.
 */
@Entity
@Table(name = "pruebas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pruebas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_de_prueba", nullable = false)
    private Integer numeroDePrueba;

    @Column(name = "longitud")
    private Float longitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDePrueba() {
        return numeroDePrueba;
    }

    public Pruebas numeroDePrueba(Integer numeroDePrueba) {
        this.numeroDePrueba = numeroDePrueba;
        return this;
    }

    public void setNumeroDePrueba(Integer numeroDePrueba) {
        this.numeroDePrueba = numeroDePrueba;
    }

    public Float getLongitud() {
        return longitud;
    }

    public Pruebas longitud(Float longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
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
        Pruebas pruebas = (Pruebas) o;
        if (pruebas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pruebas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pruebas{" +
            "id=" + getId() +
            ", numeroDePrueba=" + getNumeroDePrueba() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
