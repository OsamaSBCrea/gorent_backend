package com.gorent.api.repository;

import com.gorent.api.model.Rent;
import com.gorent.api.model.enums.RentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    Page<Rent> findByTenantId(Long tenantId, Pageable pageable);

    List<Rent> findByTenantId(Long tenantId);

    List<Rent> findByAgencyId(Long tenantId);

    List<Rent> findByAgencyIdAndTenantId(Long agencyId, Long tenantId);

    Optional<Rent> findByTenantIdAndRentStatus(Long tenantId, RentStatus status);

    List<Rent> findAllByAgencyIdAndRentStatusEquals(Long agencyId, RentStatus rentStatus);

    Page<Rent> findByAgencyIdAndRentStatusOrderByApplicationDateDesc(Long agencyId, RentStatus status, Pageable pageable);
}
