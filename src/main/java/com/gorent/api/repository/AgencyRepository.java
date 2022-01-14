package com.gorent.api.repository;

import com.gorent.api.model.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Page<Agency> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
