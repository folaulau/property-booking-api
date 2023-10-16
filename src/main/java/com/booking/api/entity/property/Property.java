package com.booking.api.entity.property;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.booking.api.entity.block.Block;
import com.booking.api.entity.booking.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Table(name = "property")
@Entity
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long              id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String            uuid;

    private String            address;

    @PrePersist
    private void preCreate() {

        if (this.uuid == null || this.uuid.isEmpty()) {
            this.uuid = "property-" + UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    private void preUpdate() {
    }
}
