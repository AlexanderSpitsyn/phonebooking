package com.asp.phonebooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "phone_booking")
@SQLDelete(sql = "UPDATE phone_booking SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PhoneBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String username;

    @NotNull
    @Column(name = "phone_id")
    private Long phoneId;

    @NotNull
    private Instant startpoint;

    @NotNull
    private Instant endpoint;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Instant getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(Instant startpoint) {
        this.startpoint = startpoint;
    }

    public Instant getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Instant endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneBooking that = (PhoneBooking) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
