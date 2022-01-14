package com.gorent.api.repository;

import com.gorent.api.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    Page<City> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<City> findByCountryId(Long countryId, Pageable pageable);

    Page<City> findByCountryIdAndNameContainingIgnoreCase(Long countryId, String name, Pageable pageable);
}
