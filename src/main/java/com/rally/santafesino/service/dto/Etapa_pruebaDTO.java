package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Etapa_prueba entity.
 */
public class Etapa_pruebaDTO implements Serializable {

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

        Etapa_pruebaDTO etapa_pruebaDTO = (Etapa_pruebaDTO) o;
        if(etapa_pruebaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapa_pruebaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Etapa_pruebaDTO{" +
            "id=" + getId() +
            "}";
    }
}
