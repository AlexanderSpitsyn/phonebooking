package com.asp.phonebooking.entity;

import com.asp.phonebooking.external.PhoneModelExternal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "phone_model")
@SQLDelete(sql = "UPDATE phone_model SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PhoneModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Example : "samsung", "htc"
     */
    @NotNull
    @Size(min = 1, max = 300)
    private String brand;

    /**
     * Example : "i9305", "A8"
     */
    @NotNull
    @Size(min = 1, max = 300)
    private String device;

    private String technology;
    private String bands2g;
    private String bands3g;
    private String bands4g;

    @Column(name = "updated_with_external_at")
    private Instant updatedWithExternalAt;

    @Column(name = "update_with_external")
    private boolean updateWithExternal = true;

    private boolean deleted;

    protected PhoneModel() {
    }

    public PhoneModel(Long id,
                      String brand,
                      String device,
                      String technology,
                      String bands2g,
                      String bands3g,
                      String bands4g,
                      boolean updateWithExternal) {
        this.id = id;
        this.brand = brand;
        this.device = device;
        this.technology = technology;
        this.bands2g = bands2g;
        this.bands3g = bands3g;
        this.bands4g = bands4g;
        this.updateWithExternal = updateWithExternal;
    }

    public PhoneModel withExternal(PhoneModelExternal external) {
        return new PhoneModel(
                id,
                brand,
                device,
                external.getTechnology(),
                external.getBands2g(),
                external.getBands3g(),
                external.getBands4g(),
                updateWithExternal
        );
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getDevice() {
        return device;
    }

    public String getTechnology() {
        return technology;
    }

    public String getBands2g() {
        return bands2g;
    }

    public String getBands3g() {
        return bands3g;
    }

    public String getBands4g() {
        return bands4g;
    }

    public Instant getUpdatedWithExternalAt() {
        return updatedWithExternalAt;
    }

    public boolean isUpdateWithExternal() {
        return updateWithExternal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneModel that = (PhoneModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
