package com.booking.api.entity.booking;

import com.booking.api.entity.property.Property;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Table(name = "booking")
@Entity
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long              id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String            uuid;

    @Column(name = "guest_name")
    private String            guestName;

    @Column(name = "number_of_guests")
    private Integer            numberOfGuests;

    @Column(name = "cancellation_reason")
    private String            cancellationReason;

    @Column(name = "start_date")
    private LocalDate         startDate;

    @Column(name = "end_date")
    private LocalDate         endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status            status;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "property_id")
    private Property property;

    @PrePersist
    private void preCreate() {

        if (this.uuid == null || this.uuid.isEmpty()) {
            this.uuid = "booking-" + UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    private void preUpdate() {
    }
}
