package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AutoTiempoPrueba.
 */
@Entity
@Table(name = "auto_tiempo_prueba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutoTiempoPrueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Auto id_auto;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Tiempos id_tiempos;

    @ManyToOne(optional = false)
    @NotNull
    private Pruebas id_prueba;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Auto getId_auto() {
        return id_auto;
    }

    public AutoTiempoPrueba id_auto(Auto auto) {
        this.id_auto = auto;
        return this;
    }

    public void setId_auto(Auto auto) {
        this.id_auto = auto;
    }

    public Tiempos getId_tiempos() {
        return id_tiempos;
    }

    public AutoTiempoPrueba id_tiempos(Tiempos tiempos) {
        this.id_tiempos = tiempos;
        return this;
    }

    public void setId_tiempos(Tiempos tiempos) {
        this.id_tiempos = tiempos;
    }

    public Pruebas getId_prueba() {
        return id_prueba;
    }

    public AutoTiempoPrueba id_prueba(Pruebas pruebas) {
        this.id_prueba = pruebas;
        return this;
    }

    public void setId_prueba(Pruebas pruebas) {
        this.id_prueba = pruebas;
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
        AutoTiempoPrueba autoTiempoPrueba = (AutoTiempoPrueba) o;
        if (autoTiempoPrueba.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autoTiempoPrueba.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutoTiempoPrueba{" +
            "id=" + getId() +
            "}";
    }
}
