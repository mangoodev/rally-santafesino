package com.rally.santafesino.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Auto_carrear entity.
 */
public class Auto_carrearDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Auto_carrearDTO auto_carrearDTO = (Auto_carrearDTO) o;
        if(auto_carrearDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auto_carrearDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auto_carrearDTO{" +
            "id=" + getId() +
            "}";
    }
}
