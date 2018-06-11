package com.rally.santafesino.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Etapa entity.
 */
public class EtapaDTO implements Serializable {

    private Long id;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EtapaDTO etapaDTO = (EtapaDTO) o;
        if(etapaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapaDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
