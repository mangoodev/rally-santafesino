package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AutoTiempoPrueba entity.
 */
public class AutoTiempoPruebaDTO implements Serializable {

    private Long id;

    private Long id_autoId;

    private Long id_tiemposId;

    private Long id_pruebaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_autoId() {
        return id_autoId;
    }

    public void setId_autoId(Long autoId) {
        this.id_autoId = autoId;
    }

    public Long getId_tiemposId() {
        return id_tiemposId;
    }

    public void setId_tiemposId(Long tiemposId) {
        this.id_tiemposId = tiemposId;
    }

    public Long getId_pruebaId() {
        return id_pruebaId;
    }

    public void setId_pruebaId(Long pruebasId) {
        this.id_pruebaId = pruebasId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutoTiempoPruebaDTO autoTiempoPruebaDTO = (AutoTiempoPruebaDTO) o;
        if(autoTiempoPruebaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autoTiempoPruebaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutoTiempoPruebaDTO{" +
            "id=" + getId() +
            "}";
    }
}
