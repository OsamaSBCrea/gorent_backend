package com.gorent.api.resource;

import com.gorent.api.dto.RentApplicationDTO;
import com.gorent.api.dto.RentHistoryDTO;
import com.gorent.api.errors.ForbiddenRequestException;
import com.gorent.api.model.Rent;
import com.gorent.api.security.SecurityUtils;
import com.gorent.api.service.RentService;
import com.gorent.api.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
public class RentResource {

    private final RentService rentService;

    private final PaginationService paginationService;

    public RentResource(RentService rentService,
                          PaginationService paginationService) {
        this.rentService = rentService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public Page<Rent> getAllRents(@Nullable @RequestParam Integer page,
                                  @Nullable @RequestParam Integer pageSize,
                                  @Nullable @RequestParam Long tenantId) {

        return tenantId == null ? this.rentService.getAllRents(paginationService.getPageable(page, pageSize))
                : rentService.getAllRentsByTenantId(tenantId, paginationService.getPageable(page, pageSize));
    }

    @GetMapping("/{rentId}")
    public ResponseEntity<Rent> getRentById(@PathVariable Long rentId) {
        Rent rent = rentService.getRentById(rentId);
        if (rent != null) {
            return ResponseEntity.ok(rent);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{rentId}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long rentId) {
        this.rentService.deleteRent(rentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/applications")
    public Page<RentApplicationDTO> getRentApplications(@Nullable @RequestParam Integer page,
                                                        @Nullable @RequestParam Integer pageSize) {
        if (SecurityUtils.getCurrentAgencyId() == null) {
            throw new ForbiddenRequestException("Tenant can't get rent applications");
        }

        return this.rentService.getRentApplications(paginationService.getPageable(page, pageSize));
    }

    @GetMapping("/history")
    public List<RentHistoryDTO> getRentHistory() {
        return rentService.getRentHistory();
    }

    @GetMapping("/history/{tenantId}")
    public List<RentHistoryDTO> getTenantHistory(@PathVariable("tenantId") Long tenantId) {
        return rentService.getTenantHistory(tenantId);
    }

    @PostMapping("approve/{rentId}")
    public ResponseEntity<Void> approveRentApplication(@PathVariable("rentId") Long rentId) {
        Rent rent = this.rentService.getRentById(rentId);
        if (rent == null) {
            return ResponseEntity.notFound().build();
        }
        this.rentService.approveRent(rent);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reject/{rentId}")
    public ResponseEntity<Void> rejectRentApplication(@PathVariable("rentId") Long rentId) {
        Rent rent = this.rentService.getRentById(rentId);
        if (rent == null) {
            return ResponseEntity.notFound().build();
        }
        this.rentService.rejectRent(rent);
        return ResponseEntity.ok().build();
    }
    
}
