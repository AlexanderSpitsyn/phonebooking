package com.asp.phonebooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "phone")
@SQLDelete(sql = "UPDATE phone SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 300)
    private String name;

    @NotNull
    @Column(name = "phone_model_id")
    private Long phoneModelId;

    private boolean deleted;

    protected Phone() {
    }

    public Phone(Long id, String name, Long phoneModelId) {
        this.id = id;
        this.name = name;
        this.phoneModelId = phoneModelId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPhoneModelId() {
        return phoneModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
