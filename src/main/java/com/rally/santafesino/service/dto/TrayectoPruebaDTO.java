package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TrayectoPrueba entity.
 */
public class TrayectoPruebaDTO implements Serializable {

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

        TrayectoPruebaDTO trayectoPruebaDTO = (TrayectoPruebaDTO) o;
        if(trayectoPruebaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trayectoPruebaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrayectoPruebaDTO{" +
            "id=" + getId() +
            "}";
    }
}
