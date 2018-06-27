package com.rally.santafesino.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tiempos.
 */
@Entity
@Table(name = "tiempos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tiempos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "parcial", nullable = false)
    private Long parcial;

    @OneToOne(mappedBy = "id_tiempos")
    @JsonIgnore
    private AutoTiempoPrueba autoTiempoPrueba;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParcial() {
        return parcial;
    }

    public Tiempos parcial(Long parcial) {
        this.parcial = parcial;
        return this;
    }

    public void setParcial(Long parcial) {
        this.parcial = parcial;
    }

    public AutoTiempoPrueba getAutoTiempoPrueba() {
        return autoTiempoPrueba;
    }

    public Tiempos autoTiempoPrueba(AutoTiempoPrueba autoTiempoPrueba) {
        this.autoTiempoPrueba = autoTiempoPrueba;
        return this;
    }

    public void setAutoTiempoPrueba(AutoTiempoPrueba autoTiempoPrueba) {
        this.autoTiempoPrueba = autoTiempoPrueba;
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
        Tiempos tiempos = (Tiempos) o;
        if (tiempos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tiempos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tiempos{" +
            "id=" + getId() +
            ", parcial=" + getParcial() +
            "}";
    }
}
