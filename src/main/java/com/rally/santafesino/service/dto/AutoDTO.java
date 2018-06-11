package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Auto entity.
 */
public class AutoDTO implements Serializable {

    private Long id;

    @NotNull
    private String marca;

    @NotNull
    private String modelo;

    @Lob
    private byte[] foto;
    private String fotoContentType;

    private String descripcion;

    @NotNull
    private String clase;

    private Long copilotoId;

    private Long pilotoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Long getCopilotoId() {
        return copilotoId;
    }

    public void setCopilotoId(Long personaId) {
        this.copilotoId = personaId;
    }

    public Long getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(Long personaId) {
        this.pilotoId = personaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutoDTO autoDTO = (AutoDTO) o;
        if(autoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutoDTO{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", foto='" + getFoto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", clase='" + getClase() + "'" +
            "}";
    }
}
