package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Trayecto_prueba entity.
 */
public class Trayecto_pruebaDTO implements Serializable {

    private Long id;

    private Long id_pruebaId;

    private Long id_trayectoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_pruebaId() {
        return id_pruebaId;
    }

    public void setId_pruebaId(Long pruebasId) {
        this.id_pruebaId = pruebasId;
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

        Trayecto_pruebaDTO trayecto_pruebaDTO = (Trayecto_pruebaDTO) o;
        if(trayecto_pruebaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trayecto_pruebaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trayecto_pruebaDTO{" +
            "id=" + getId() +
            "}";
    }
}
