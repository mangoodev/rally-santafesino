package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Localidad_carrera entity.
 */
public class Localidad_carreraDTO implements Serializable {

    private Long id;

    private Long id_carreraId;

    private Long id_localidadId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_carreraId() {
        return id_carreraId;
    }

    public void setId_carreraId(Long carreraId) {
        this.id_carreraId = carreraId;
    }

    public Long getId_localidadId() {
        return id_localidadId;
    }

    public void setId_localidadId(Long localidadId) {
        this.id_localidadId = localidadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Localidad_carreraDTO localidad_carreraDTO = (Localidad_carreraDTO) o;
        if(localidad_carreraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localidad_carreraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Localidad_carreraDTO{" +
            "id=" + getId() +
            "}";
    }
}
