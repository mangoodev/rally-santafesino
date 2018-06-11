package com.rally.santafesino.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Localidad entity.
 */
public class LocalidadDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalidadDTO localidadDTO = (LocalidadDTO) o;
        if(localidadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localidadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalidadDTO{" +
            "id=" + getId() +
            "}";
    }
}
