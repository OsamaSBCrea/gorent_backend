package com.gorent.api.service;

import com.gorent.api.dto.RentApplicationDTO;
import com.gorent.api.dto.RentHistoryDTO;
import com.gorent.api.mapper.RentMapper;
import com.gorent.api.model.Rent;
import com.gorent.api.model.Tenant;
import com.gorent.api.model.enums.RentStatus;
import com.gorent.api.notifications.FCMService;
import com.gorent.api.notifications.PushNotification;
import com.gorent.api.repository.RentRepository;
import com.gorent.api.repository.TenantRepository;
import com.gorent.api.security.DomainUserDetails;
import com.gorent.api.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class RentService {

    private final RentRepository rentRepository;

    private final TenantRepository tenantRepository;

    private final RentMapper rentMapper;

    private final FCMService fcmService;

    private final Logger log = LoggerFactory.getLogger(RentService.class);

    public RentService(RentRepository rentRepository,
                       TenantRepository tenantRepository,
                       FCMService fcmService,
                       RentMapper rentMapper) {
        this.rentRepository = rentRepository;
        this.tenantRepository = tenantRepository;
        this.fcmService = fcmService;
        this.rentMapper = rentMapper;
    }

    public Page<Rent> getAllRents(Pageable pageable) {
        return this.rentRepository.findAll(pageable);
    }

    public Rent getRentById(Long id) {
        return rentRepository.findById(id).orElse(null);
    }

    public Page<Rent> getAllRentsByTenantId(Long tenantId, Pageable pageable) {
        return rentRepository.findByTenantId(tenantId, pageable);
    }

    public void deleteRent(Long id) {
        this.rentRepository.deleteById(id);
    }

    public List<Rent> getAllNewRents(Long agencyId) {
        List<Rent> rents = this.rentRepository.findAllByAgencyIdAndRentStatusEquals(agencyId, RentStatus.NEW_RENT);
        rents.forEach(rent -> {
            rent.setRentStatus(RentStatus.PENDING);
            this.rentRepository.save(rent);
        });
        return rents;
    }

    public Rent getCurrentRentByTenantId(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        if (tenant != null && tenant.getCurrentRentId() != null) {
            return rentRepository.findById(tenant.getCurrentRentId()).orElse(null);
        }
        return null;
    }

    public Page<RentApplicationDTO> getRentApplications(Pageable pageable) {
        Long agencyId = SecurityUtils.getCurrentAgencyId();
        Page<Rent> rents = this.rentRepository
                .findByAgencyIdAndRentStatusOrderByApplicationDateDesc(agencyId, RentStatus.PENDING, pageable);
        return this.rentMapper.rentsToRentApplicationsDTO(rents);
    }

    public List<RentHistoryDTO> getRentHistory() {
        if (SecurityUtils.getCurrentAgencyId() != null) {
            return rentRepository.findByAgencyId(SecurityUtils.getCurrentAgencyId())
                    .stream().map(RentHistoryDTO::new).collect(Collectors.toList());
        }

        if (SecurityUtils.getCurrentTenantId() != null) {
            return rentRepository.findByTenantId(SecurityUtils.getCurrentTenantId())
                    .stream().map(RentHistoryDTO::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<RentHistoryDTO> getTenantHistory(Long tenantId) {
        return rentRepository.findByAgencyIdAndTenantId(SecurityUtils.getCurrentAgencyId(), tenantId)
                .stream().map(RentHistoryDTO::new).collect(Collectors.toList());
    }

    public void approveRent(Rent rent) {
        rent.setRentStatus(RentStatus.APPROVED);
        rentRepository.save(rent);
        try {
            fcmService.sendMessageToToken(new PushNotification(
                    "Rent Application Approved",
                    "Your rent application for a property located in " + rent.getProperty().getCity().getName()
                    + " has been approved by agency: " + rent.getAgency().getName(),
                    "RENT_APPROVE",
                    rent.getTenant().getFcmToken()
            ), rent.getProperty().getId());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void rejectRent(Rent rent) {
        rent.setRentStatus(RentStatus.REJECTED);
        rentRepository.save(rent);
        try {
            fcmService.sendMessageToToken(new PushNotification(
                    "Rent Application Rejected",
                    "Your rent application for a property located in " + rent.getProperty().getCity().getName()
                            + " has been rejected by agency: " + rent.getAgency().getName(),
                    "RENT_REJECT",
                    rent.getTenant().getFcmToken()
            ), rent.getProperty().getId());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
