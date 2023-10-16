package com.booking.api.entity.block;
import com.booking.api.entity.booking.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByUuid(String uuid);

    @Query("SELECT bl FROM Block bl WHERE bl.property.id = :propertyId and bl.status = :status and (bl.startDate <= :endDate AND bl.endDate >= :startDate)")
    List<Block> getBlocksByDates(@Param(value = "propertyId") Long propertyId,
                                 @Param(value = "status") Status status,
                                 @Param(value = "startDate") LocalDate startDate,
                                 @Param(value = "endDate") LocalDate endDate);
}
