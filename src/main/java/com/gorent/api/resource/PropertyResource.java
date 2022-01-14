package com.gorent.api.resource;

import com.gorent.api.dto.PropertyDTO;
import com.gorent.api.errors.ForbiddenRequestException;
import com.gorent.api.model.Property;
import com.gorent.api.model.Rent;
import com.gorent.api.security.SecurityUtils;
import com.gorent.api.service.PaginationService;
import com.gorent.api.service.PropertyService;
import com.gorent.api.util.search.specification.PropertySpecification;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/property")
public class PropertyResource {

    private final PropertyService propertyService;

    private final PaginationService paginationService;

    public PropertyResource(PropertyService propertyService,
                            PaginationService paginationService) {
        this.propertyService = propertyService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public Page<Property> getAllProperties(@Nullable @RequestParam Integer page,
                                           @Nullable @RequestParam Integer pageSize) {

        return this.propertyService.getProperties(paginationService.getPageable(page, pageSize));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> getProperty(@PathVariable Long propertyId) {
        Optional<Property> propertyOptional = this.propertyService.getPropertyById(propertyId);
        if (propertyOptional.isPresent()) {
           return ResponseEntity.ok(propertyOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Property>> getLatestProperties() {
        List<Property> latestProperties = this.propertyService.getLatestProperties();

        return ResponseEntity.ok(latestProperties);
    }

    @PostMapping("")
    public ResponseEntity<Property> createProperty(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        Property property = propertyService.createProperty(propertyDTO);

        if (property != null) {
            return ResponseEntity.created(new URI("/api/property/" + property.getId())).body(property);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<Void> uploadPropertyPhotos(@PathVariable Long propertyId,
                                                     @RequestParam("photos") MultipartFile[] files) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public Page<Property> searchProperties(
            @Nullable @RequestParam Integer page,
            @Nullable @RequestParam Integer pageSize,
            @RequestBody PropertySpecification propertySpecification) {
        return this.propertyService.searchProperties(propertySpecification, paginationService.getPageable(page, pageSize));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable("propertyId") Long propertyId) {
        Property property = this.propertyService.getPropertyById(propertyId).orElse(null);

        if (property == null) {
            return ResponseEntity.notFound().build();
        } else if(!SecurityUtils.getCurrentAgencyId().equals(property.getAgency().getId())) {
            throw new ForbiddenRequestException();
        } else {
            this.propertyService.deleteProperty(propertyId);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<Void> updateProperty(@PathVariable("propertyId") Long propertyId,
                                               @RequestBody PropertyDTO propertyDTO) {
        Property property = this.propertyService.getPropertyById(propertyId).orElse(null);
        if (property == null) {
            return ResponseEntity.notFound().build();
        }

        if (SecurityUtils.getCurrentAgencyId() == null ||
                !SecurityUtils.getCurrentAgencyId().equals(property.getAgency().getId())) {
            throw new ForbiddenRequestException("Cannot update property. Access Denied");
        }

        this.propertyService.updateProperty(property, propertyDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/rent")
    public ResponseEntity<Rent> rentProperty(@PathVariable("propertyId") Long propertyId) {
        Property property = this.propertyService.getPropertyById(propertyId).orElse(null);
        if (property == null) {
            return ResponseEntity.notFound().build();
        } else if (!property.getCity().getCountry().getId().equals(SecurityUtils.getCurrentCountryId())) {
            throw new ForbiddenRequestException();
        }

        Rent rent = this.propertyService.rentProperty(propertyId);
        if (rent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(rent);
        }
    }

}
