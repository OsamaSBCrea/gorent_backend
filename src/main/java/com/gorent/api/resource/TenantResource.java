package com.gorent.api.resource;

import com.gorent.api.dto.TenantDTORegister;
import com.gorent.api.model.Tenant;
import com.gorent.api.service.TenantService;
import com.gorent.api.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantResource {

    private final TenantService tenantService;

    private final PaginationService paginationService;

    public TenantResource(TenantService tenantService,
                          PaginationService paginationService) {
        this.tenantService = tenantService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public Page<Tenant> getAllTenants(@Nullable @RequestParam Integer page,
                                       @Nullable @RequestParam Integer pageSize,
                                       @Nullable @RequestParam String search) {
        if (search == null) {
            return this.tenantService.getAllTenants(paginationService.getPageable(page, pageSize));
        } else {
            return this.tenantService.searchTenants(search, paginationService.getPageable(page, pageSize));
        }
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long tenantId) {
        Tenant tenant = tenantService.getTenantById(tenantId);
        if (tenant != null) {
            return ResponseEntity.ok(tenant);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{tenantId}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long tenantId, @RequestBody Tenant tenant) {
        Tenant newTenant = this.tenantService.updateTenant(tenantId, tenant);

        if (newTenant != null) {
            return ResponseEntity
                    .ok(newTenant);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long tenantId) {
        this.tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
    
}
