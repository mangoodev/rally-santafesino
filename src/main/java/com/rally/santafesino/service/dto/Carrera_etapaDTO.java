package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Carrera_etapa entity.
 */
public class Carrera_etapaDTO implements Serializable {

    private Long id;

    private Long id_carreraId;

    private Long id_etapaId;

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

    public Long getId_etapaId() {
        return id_etapaId;
    }

    public void setId_etapaId(Long etapaId) {
        this.id_etapaId = etapaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Carrera_etapaDTO carrera_etapaDTO = (Carrera_etapaDTO) o;
        if(carrera_etapaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carrera_etapaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Carrera_etapaDTO{" +
            "id=" + getId() +
            "}";
    }
}
