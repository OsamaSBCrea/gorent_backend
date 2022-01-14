package com.gorent.api.repository;

import com.gorent.api.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    List<Property> findTop5ByCityCountryIdOrderByCreatedDateDesc(Long countryId);

    Page<Property> findAllByCityCountryIdOrderByCreatedDateDesc(Long countryId, Pageable pageable);

    Page<Property> findAllByAgencyIdOrderByCreatedDateDesc(Long agencyId, Pageable pageable);
}
