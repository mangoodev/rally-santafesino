package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Tiempos entity.
 */
public class TiemposDTO implements Serializable {

    private Long id;

    @NotNull
    private Long parcial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParcial() {
        return parcial;
    }

    public void setParcial(Long parcial) {
        this.parcial = parcial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TiemposDTO tiemposDTO = (TiemposDTO) o;
        if(tiemposDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tiemposDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TiemposDTO{" +
            "id=" + getId() +
            ", parcial=" + getParcial() +
            "}";
    }
}
