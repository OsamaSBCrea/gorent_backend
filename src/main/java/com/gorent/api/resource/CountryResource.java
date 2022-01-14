package com.gorent.api.resource;

import com.gorent.api.dto.CountryDTO;
import com.gorent.api.model.Country;
import com.gorent.api.service.CountryService;
import com.gorent.api.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/country")
public class CountryResource {

    private final CountryService countryService;

    private final PaginationService paginationService;

    public CountryResource(CountryService countryService,
                           PaginationService paginationService) {
        this.countryService = countryService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public Page<Country> getAllCountries(@Nullable @RequestParam Integer page,
                                         @Nullable @RequestParam Integer pageSize,
                                         @Nullable @RequestParam String search) {
        if (search == null) {
            return this.countryService.getAllCountries(paginationService.getPageable(page, pageSize));
        } else {
            return this.countryService.searchCountries(search, paginationService.getPageable(page, pageSize));
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long countryId) {
        Country country = countryService.getCountryById(countryId);
        if (country != null) {
            return ResponseEntity.ok(country);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Country> createCountry(@RequestBody CountryDTO countryDTO) throws URISyntaxException {
        Country country = this.countryService.createCountry(countryDTO);

        if (country != null) {
            return ResponseEntity
                    .created(new URI("/api/country/" + country.getId()))
                    .body(country);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long countryId, CountryDTO countryDTO) {
        Country country = this.countryService.updateCountry(countryId, countryDTO);

        if (country != null) {
            return ResponseEntity
                    .ok(country);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long countryId) {
        this.countryService.deleteCountry(countryId);
        return ResponseEntity.noContent().build();
    }
}
