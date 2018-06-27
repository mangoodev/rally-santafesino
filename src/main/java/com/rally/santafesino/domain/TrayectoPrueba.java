package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TrayectoPrueba.
 */
@Entity
@Table(name = "trayecto_prueba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrayectoPrueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Pruebas id_prueba;

    @ManyToOne(optional = false)
    @NotNull
    private Trayecto id_trayecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pruebas getId_prueba() {
        return id_prueba;
    }

    public TrayectoPrueba id_prueba(Pruebas pruebas) {
        this.id_prueba = pruebas;
        return this;
    }

    public void setId_prueba(Pruebas pruebas) {
        this.id_prueba = pruebas;
    }

    public Trayecto getId_trayecto() {
        return id_trayecto;
    }

    public TrayectoPrueba id_trayecto(Trayecto trayecto) {
        this.id_trayecto = trayecto;
        return this;
    }

    public void setId_trayecto(Trayecto trayecto) {
        this.id_trayecto = trayecto;
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
        TrayectoPrueba trayectoPrueba = (TrayectoPrueba) o;
        if (trayectoPrueba.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trayectoPrueba.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrayectoPrueba{" +
            "id=" + getId() +
            "}";
    }
}
