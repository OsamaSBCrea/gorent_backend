package com.gorent.api.service;

import com.gorent.api.dto.TenantDTORegister;
import com.gorent.api.model.Tenant;
import com.gorent.api.model.User;
import com.gorent.api.repository.TenantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Page<Tenant> getAllTenants(Pageable pageable) {
        return this.tenantRepository.findAll(pageable);
    }

    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }

    public Tenant createTenant(TenantDTORegister tenantDTO, User user) {
        Tenant tenant = new Tenant(tenantDTO);
        tenant.setUser(user);
        return this.tenantRepository.save(tenant);
    }

    public Tenant updateTenant(Long id, Tenant tenant) {
        Optional<Tenant> tenantOptional = tenantRepository.findById(id);

        if(tenantOptional.isPresent()) {
            Tenant existingTenant = tenantOptional.get();
            existingTenant.setFirstName(tenant.getFirstName());
            existingTenant.setLastName(tenant.getLastName());
            existingTenant.setNationality(tenant.getNationality());
            existingTenant.setGender(tenant.getGender());
            existingTenant.setFamilySize(tenant.getFamilySize());
            existingTenant.setGrossMonthlySalary(tenant.getGrossMonthlySalary());
            existingTenant.setOccupation(tenant.getOccupation());
            return tenantRepository.save(existingTenant);
        }

        return null;
    }

    public void deleteTenant(Long id) {
        this.tenantRepository.deleteById(id);
    }

    public Page<Tenant> searchTenants(String search, Pageable pageable) {
        return this.tenantRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search, pageable);
    }
    
}
