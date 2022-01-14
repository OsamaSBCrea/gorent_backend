package com.gorent.api.service;

import com.gorent.api.dto.PropertyDTO;
import com.gorent.api.errors.ForbiddenRequestException;
import com.gorent.api.mapper.PropertyMapper;
import com.gorent.api.model.Agency;
import com.gorent.api.model.Property;
import com.gorent.api.model.Rent;
import com.gorent.api.model.Tenant;
import com.gorent.api.model.enums.RentStatus;
import com.gorent.api.notifications.FCMService;
import com.gorent.api.notifications.PushNotification;
import com.gorent.api.repository.PropertyRepository;
import com.gorent.api.repository.RentRepository;
import com.gorent.api.repository.TenantRepository;
import com.gorent.api.security.SecurityUtils;
import com.gorent.api.util.search.specification.PropertySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    private final TenantRepository tenantRepository;

    private final RentRepository rentRepository;

    private final PropertyMapper propertyMapper;

    private final FCMService fcmService;

    public PropertyService(PropertyRepository propertyRepository,
                           TenantRepository tenantRepository,
                           PropertyMapper propertyMapper,
                           RentRepository rentRepository,
                           FCMService fcmService) {
        this.propertyRepository = propertyRepository;
        this.tenantRepository = tenantRepository;
        this.propertyMapper = propertyMapper;
        this.rentRepository = rentRepository;
        this.fcmService = fcmService;
    }

    public Page<Property> getProperties(Pageable pageable) {
        return propertyRepository.findAllByAgencyIdOrderByCreatedDateDesc(SecurityUtils.getCurrentAgencyId(), pageable);
    }

    public Optional<Property> getPropertyById(Long propertyId) {
        return this.propertyRepository.findById(propertyId);
    }

    public Property createProperty(PropertyDTO propertyDTO) {
        Property property = propertyMapper.propertyDtoToProperty(propertyDTO);
        property.setCreatedDate(Instant.now());
        property.setAgency(new Agency(SecurityUtils.getCurrentAgencyId()));
        return this.propertyRepository.save(property);
    }

    public List<Property> getLatestProperties() {
        return this.propertyRepository.findTop5ByCityCountryIdOrderByCreatedDateDesc(SecurityUtils.getCurrentCountryId());
    }

    public Page<Property> searchProperties(PropertySpecification propertySpecification, Pageable pageable) {
        return this.propertyRepository.findAll(propertySpecification.getSpecification(), pageable);
    }

    public void deleteProperty(Long propertyId) {
        this.propertyRepository.delete(new Property(propertyId));
    }

    public Rent rentProperty(Long propertyId) {
        Property property = this.propertyRepository.findById(propertyId).orElse(null);
        Tenant tenant = tenantRepository.findById(SecurityUtils.getCurrentTenantId()).orElse(null);

        if (property == null) {
            return null;
        } else if (tenant != null && tenant.getCurrentRentId() != null) {
            throw new ForbiddenRequestException("Tenant already has a rent application");
        } else if (tenant != null) {
            Rent rent = new Rent();
            rent.setTenant(tenant);
            rent.setProperty(new Property(propertyId));
            rent.setAgency(new Agency(property.getAgency().getId()));
            rent.setApplicationDate(Instant.now());
            rent.setRentStatus(RentStatus.NEW_RENT);
            Rent newRent = this.rentRepository.save(rent);
            tenant.setCurrentRentId(newRent.getId());
            tenantRepository.save(tenant);
            try {
                fcmService.sendMessageToToken(new PushNotification(
                        "New Rent Application",
                        "The tenant " + tenant.getFullName() +
                                " has requested a rent for your property located in " + property.getCity().getName(),
                        "RENT",
                        property.getAgency().getFcmToken()
                ), property.getId());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return newRent;
        } else {
            return null;
        }
    }

    public void updateProperty(Property property, PropertyDTO propertyDTO) {
        Property newProperty = new Property(propertyDTO);
        newProperty.setId(property.getId());
        newProperty.setAgency(property.getAgency());
        newProperty.setCity(property.getCity());
        newProperty.setCreatedDate(property.getCreatedDate());
        propertyRepository.save(newProperty);
    }
}
