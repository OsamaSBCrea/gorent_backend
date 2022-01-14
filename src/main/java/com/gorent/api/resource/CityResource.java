package com.gorent.api.resource;

import com.gorent.api.dto.CityDTO;
import com.gorent.api.model.City;
import com.gorent.api.service.CityService;
import com.gorent.api.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/city")
public class CityResource {

    private final CityService cityService;

    private final PaginationService paginationService;

    public CityResource(CityService cityService,
                           PaginationService paginationService) {
        this.cityService = cityService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public Page<City> getAllCities(@Nullable @RequestParam Integer page,
                                   @Nullable @RequestParam Integer pageSize,
                                   @Nullable @RequestParam String search,
                                   @Nullable @RequestParam Long countryId) {
        if (countryId != null) {
            return this.cityService.getAllCitiesByCountryId(countryId, search, paginationService.getPageable(page, pageSize));
        }
        if (search == null) {
            return this.cityService.getAllCities(paginationService.getPageable(page, pageSize));
        } else {
            return this.cityService.searchCities(search, paginationService.getPageable(page, pageSize));
        }
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> getCityById(@PathVariable Long cityId) {
        City city = cityService.getCityById(cityId);
        if (city != null) {
            return ResponseEntity.ok(city);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<City> createCity(@RequestBody CityDTO cityDTO) throws URISyntaxException {
        if (cityDTO.getCountryId() == null) {
            return ResponseEntity.badRequest().build();
        }
        City city = this.cityService.createCity(cityDTO);

        if (city != null) {
            return ResponseEntity
                    .created(new URI("/api/city/" + city.getId()))
                    .body(city);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> updateCity(@PathVariable Long cityId, CityDTO cityDTO) {
        City city = this.cityService.updateCity(cityId, cityDTO);

        if (city != null) {
            return ResponseEntity
                    .ok(city);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long cityId) {
        this.cityService.deleteCity(cityId);
        return ResponseEntity.noContent().build();
    }
    
}
