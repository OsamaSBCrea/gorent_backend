package com.gorent.api.service;

import com.gorent.api.dto.CityDTO;
import com.gorent.api.model.City;
import com.gorent.api.model.Country;
import com.gorent.api.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getAllCities(Pageable pageable) {
        return this.cityRepository.findAll(pageable);
    }

    public Page<City> getAllCitiesByCountryId(Long id, String search, Pageable pageable) {
        return search == null ? this.cityRepository.findByCountryId(id, pageable)
                : this.cityRepository.findByCountryIdAndNameContainingIgnoreCase(id, search, pageable);
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City createCity(CityDTO cityDTO) {
        City city = new City(cityDTO);
        return this.cityRepository.save(city);
    }

    public City updateCity(Long id, CityDTO cityDTO) {
        Optional<City> cityOptional = cityRepository.findById(id);

        if(cityOptional.isPresent()) {
            City city = cityOptional.get();
            city.setName(cityDTO.getName());
            if (!city.getCountry().getId().equals(cityDTO.getCountryId())) {
                city.setCountry(new Country(cityDTO.getCountryId()));
            }
            city = cityRepository.save(city);
            return city;
        }

        return null;
    }

    public void deleteCity(Long id) {
        this.cityRepository.deleteById(id);
    }

    public Page<City> searchCities(String search, Pageable pageable) {
        return this.cityRepository.findByNameContainingIgnoreCase(search, pageable);
    }
    
}
