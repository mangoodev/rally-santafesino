package com.rally.santafesino.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Auto_carrera entity.
 */
public class Auto_carreraDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime creation_date;

    private Long autoId;

    private Long carreraId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(ZonedDateTime creation_date) {
        this.creation_date = creation_date;
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

        Auto_carreraDTO auto_carreraDTO = (Auto_carreraDTO) o;
        if(auto_carreraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auto_carreraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auto_carreraDTO{" +
            "id=" + getId() +
            ", creation_date='" + getCreation_date() + "'" +
            "}";
    }
}
