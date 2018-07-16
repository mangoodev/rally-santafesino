package com.rally.santafesino.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CarreraClase entity.
 */
public class CarreraClaseDTO implements Serializable {

    private Long id;

    private Long carreraId;

    private Long claseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public Long getClaseId() {
        return claseId;
    }

    public void setClaseId(Long claseId) {
        this.claseId = claseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarreraClaseDTO carreraClaseDTO = (CarreraClaseDTO) o;
        if(carreraClaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carreraClaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarreraClaseDTO{" +
            "id=" + getId() +
            "}";
    }
}
