package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Auto_tiempo_prueba.
 */
@Entity
@Table(name = "auto_tiempo_prueba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auto_tiempo_prueba implements Serializable {

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

    public Auto_tiempo_prueba id_auto(Auto auto) {
        this.id_auto = auto;
        return this;
    }

    public void setId_auto(Auto auto) {
        this.id_auto = auto;
    }

    public Tiempos getId_tiempos() {
        return id_tiempos;
    }

    public Auto_tiempo_prueba id_tiempos(Tiempos tiempos) {
        this.id_tiempos = tiempos;
        return this;
    }

    public void setId_tiempos(Tiempos tiempos) {
        this.id_tiempos = tiempos;
    }

    public Pruebas getId_prueba() {
        return id_prueba;
    }

    public Auto_tiempo_prueba id_prueba(Pruebas pruebas) {
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
        Auto_tiempo_prueba auto_tiempo_prueba = (Auto_tiempo_prueba) o;
        if (auto_tiempo_prueba.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auto_tiempo_prueba.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auto_tiempo_prueba{" +
            "id=" + getId() +
            "}";
    }
}
