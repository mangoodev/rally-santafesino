package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CoordenadaTrayecto entity.
 */
public class CoordenadaTrayectoDTO implements Serializable {

    private Long id;

    private Long id_coordenadasId;

    private Long id_trayectoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_coordenadasId() {
        return id_coordenadasId;
    }

    public void setId_coordenadasId(Long coordenadasId) {
        this.id_coordenadasId = coordenadasId;
    }

    public Long getId_trayectoId() {
        return id_trayectoId;
    }

    public void setId_trayectoId(Long trayectoId) {
        this.id_trayectoId = trayectoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoordenadaTrayectoDTO coordenadaTrayectoDTO = (CoordenadaTrayectoDTO) o;
        if(coordenadaTrayectoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordenadaTrayectoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoordenadaTrayectoDTO{" +
            "id=" + getId() +
            "}";
    }
}
