package com.booking.api.entity.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByUuid(String uuid);

    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId and b.status = :status and  (b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Booking> getBookingsByDates(@Param(value = "propertyId") Long propertyId,
                                     @Param(value = "status") Status status,
                                     @Param(value = "startDate") LocalDate startDate, @Param(value = "endDate") LocalDate endDate);
}