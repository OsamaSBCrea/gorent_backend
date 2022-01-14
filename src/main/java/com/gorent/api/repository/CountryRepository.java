package com.gorent.api.repository;

import com.gorent.api.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Page<Country> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
