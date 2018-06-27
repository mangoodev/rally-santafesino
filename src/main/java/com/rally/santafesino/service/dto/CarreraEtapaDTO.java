package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CarreraEtapa entity.
 */
public class CarreraEtapaDTO implements Serializable {

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

        CarreraEtapaDTO carreraEtapaDTO = (CarreraEtapaDTO) o;
        if(carreraEtapaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carreraEtapaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarreraEtapaDTO{" +
            "id=" + getId() +
            "}";
    }
}
