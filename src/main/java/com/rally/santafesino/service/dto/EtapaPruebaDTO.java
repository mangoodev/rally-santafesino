package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EtapaPrueba entity.
 */
public class EtapaPruebaDTO implements Serializable {

    private Long id;

    private Long id_etapaId;

    private Long id_pruebaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_etapaId() {
        return id_etapaId;
    }

    public void setId_etapaId(Long etapaId) {
        this.id_etapaId = etapaId;
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

        EtapaPruebaDTO etapaPruebaDTO = (EtapaPruebaDTO) o;
        if(etapaPruebaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapaPruebaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapaPruebaDTO{" +
            "id=" + getId() +
            "}";
    }
}
