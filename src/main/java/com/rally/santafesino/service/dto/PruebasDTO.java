package com.rally.santafesino.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pruebas entity.
 */
public class PruebasDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer numeroDePrueba;

    private Float longitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDePrueba() {
        return numeroDePrueba;
    }

    public void setNumeroDePrueba(Integer numeroDePrueba) {
        this.numeroDePrueba = numeroDePrueba;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PruebasDTO pruebasDTO = (PruebasDTO) o;
        if(pruebasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pruebasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PruebasDTO{" +
            "id=" + getId() +
            ", numeroDePrueba=" + getNumeroDePrueba() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
