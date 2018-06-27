package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EtapaPrueba.
 */
@Entity
@Table(name = "etapa_prueba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EtapaPrueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Etapa id_etapa;

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

    public Etapa getId_etapa() {
        return id_etapa;
    }

    public EtapaPrueba id_etapa(Etapa etapa) {
        this.id_etapa = etapa;
        return this;
    }

    public void setId_etapa(Etapa etapa) {
        this.id_etapa = etapa;
    }

    public Pruebas getId_prueba() {
        return id_prueba;
    }

    public EtapaPrueba id_prueba(Pruebas pruebas) {
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
        EtapaPrueba etapaPrueba = (EtapaPrueba) o;
        if (etapaPrueba.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapaPrueba.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapaPrueba{" +
            "id=" + getId() +
            "}";
    }
}
