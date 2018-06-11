package com.rally.santafesino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Auto.
 */
@Entity
@Table(name = "auto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "marca", nullable = false)
    private String marca;

    @NotNull
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "clase", nullable = false)
    private String clase;

    @ManyToOne
    private Persona copiloto;

    @ManyToOne
    private Persona piloto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public Auto marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Auto modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Auto foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Auto fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Auto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClase() {
        return clase;
    }

    public Auto clase(String clase) {
        this.clase = clase;
        return this;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Persona getCopiloto() {
        return copiloto;
    }

    public Auto copiloto(Persona persona) {
        this.copiloto = persona;
        return this;
    }

    public void setCopiloto(Persona persona) {
        this.copiloto = persona;
    }

    public Persona getPiloto() {
        return piloto;
    }

    public Auto piloto(Persona persona) {
        this.piloto = persona;
        return this;
    }

    public void setPiloto(Persona persona) {
        this.piloto = persona;
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
        Auto auto = (Auto) o;
        if (auto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auto{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", clase='" + getClase() + "'" +
            "}";
    }
}
