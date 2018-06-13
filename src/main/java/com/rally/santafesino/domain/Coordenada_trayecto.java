package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Coordenada_trayecto.
 */
@Entity
@Table(name = "coordenada_trayecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coordenada_trayecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Coordenadas id_coordenadas;

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

    public Coordenadas getId_coordenadas() {
        return id_coordenadas;
    }

    public Coordenada_trayecto id_coordenadas(Coordenadas coordenadas) {
        this.id_coordenadas = coordenadas;
        return this;
    }

    public void setId_coordenadas(Coordenadas coordenadas) {
        this.id_coordenadas = coordenadas;
    }

    public Trayecto getId_trayecto() {
        return id_trayecto;
    }

    public Coordenada_trayecto id_trayecto(Trayecto trayecto) {
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
        Coordenada_trayecto coordenada_trayecto = (Coordenada_trayecto) o;
        if (coordenada_trayecto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordenada_trayecto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coordenada_trayecto{" +
            "id=" + getId() +
            "}";
    }
}
