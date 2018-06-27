package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LocalidadCarrera entity.
 */
public class LocalidadCarreraDTO implements Serializable {

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

        LocalidadCarreraDTO localidadCarreraDTO = (LocalidadCarreraDTO) o;
        if(localidadCarreraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localidadCarreraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalidadCarreraDTO{" +
            "id=" + getId() +
            "}";
    }
}
