package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Coordenadas.
 */
@Entity
@Table(name = "coordenadas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coordenadas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "latitud", precision=10, scale=2, nullable = false)
    private BigDecimal latitud;

    @NotNull
    @Column(name = "longitud", precision=10, scale=2, nullable = false)
    private BigDecimal longitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public Coordenadas latitud(BigDecimal latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public Coordenadas longitud(BigDecimal longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(BigDecimal longitud) {
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
        Coordenadas coordenadas = (Coordenadas) o;
        if (coordenadas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordenadas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
            "id=" + getId() +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
