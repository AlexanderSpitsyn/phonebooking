package com.asp.phonebooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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

    /**
     * Who booked
     */
    @NotEmpty
    private String username;

    /**
     * Which phone is booked
     */
    @NotNull
    @Column(name = "phone_id")
    private Long phoneId;

    /**
     * When booked
     */
    @NotNull
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * Booked from date/time
     */
    @NotNull
    private Instant startpoint;

    /**
     * Booked to date/time
     */
    @NotNull
    private Instant endpoint;

    private boolean deleted;

    protected PhoneBooking() {
    }

    public PhoneBooking(Long id,
                        String username,
                        Long phoneId,
                        Instant createdAt,
                        Instant startpoint,
                        Instant endpoint) {
        this.id = id;
        this.username = username;
        this.phoneId = phoneId;
        this.createdAt = createdAt;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    @PrePersist
    protected void fillCreatedAt() {
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getStartpoint() {
        return startpoint;
    }

    public Instant getEndpoint() {
        return endpoint;
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
