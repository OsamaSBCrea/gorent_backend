package com.gorent.api.service;

import com.gorent.api.dto.CountryDTO;
import com.gorent.api.model.Country;
import com.gorent.api.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Page<Country> getAllCountries(Pageable pageable) {
        return this.countryRepository.findAll(pageable);
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    public Country createCountry(CountryDTO countryDTO) {
        Country country = new Country(countryDTO);
        return this.countryRepository.save(country);
    }

    public Country updateCountry(Long id, CountryDTO countryDTO) {
        Optional<Country> countryOptional = countryRepository.findById(id);

        if(countryOptional.isPresent()) {
            Country country = countryOptional.get();
            country.setName(countryDTO.getName());
            country.setCountryCode(countryDTO.getCountryCode());
            country.setCurrency(countryDTO.getCurrency());
            country = countryRepository.save(country);
            return country;
        }

        return null;
    }

    public void deleteCountry(Long id) {
        this.countryRepository.deleteById(id);
    }

    public Page<Country> searchCountries(String search, Pageable pageable) {
        return this.countryRepository.findByNameContainingIgnoreCase(search, pageable);
    }
}
