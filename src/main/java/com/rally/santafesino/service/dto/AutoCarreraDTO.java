package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AutoCarrera entity.
 */
public class AutoCarreraDTO implements Serializable {

    private Long id;

    private Integer posicion;

    private Long autoId;

    private Long carreraId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutoCarreraDTO autoCarreraDTO = (AutoCarreraDTO) o;
        if(autoCarreraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autoCarreraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutoCarreraDTO{" +
            "id=" + getId() +
            ", posicion=" + getPosicion() +
            "}";
    }
}
