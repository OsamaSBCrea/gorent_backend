package com.gorent.api.resource;

import com.gorent.api.dto.AgencyDTORegister;
import com.gorent.api.model.Agency;
import com.gorent.api.service.AgencyService;
import com.gorent.api.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agency")
public class AgencyResource {

    private final AgencyService agencyService;

    private final PaginationService paginationService;

    public AgencyResource(AgencyService agencyService,
                           PaginationService paginationService) {
        this.agencyService = agencyService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public Page<Agency> getAllAgencies(@Nullable @RequestParam Integer page,
                                       @Nullable @RequestParam Integer pageSize,
                                       @Nullable @RequestParam String search) {
        if (search == null) {
            return this.agencyService.getAllAgencies(paginationService.getPageable(page, pageSize));
        } else {
            return this.agencyService.searchAgencies(search, paginationService.getPageable(page, pageSize));
        }
    }

    @GetMapping("/{agencyId}")
    public ResponseEntity<Agency> getAgencyById(@PathVariable Long agencyId) {
        Agency agency = agencyService.getAgencyById(agencyId);
        if (agency != null) {
            return ResponseEntity.ok(agency);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{agencyId}")
    public ResponseEntity<Agency> updateAgency(@PathVariable Long agencyId, @RequestBody Agency agency) {
        Agency newAgency = this.agencyService.updateAgency(agencyId, agency);

        if (newAgency != null) {
            return ResponseEntity
                    .ok(newAgency);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{agencyId}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long agencyId) {
        this.agencyService.deleteAgency(agencyId);
        return ResponseEntity.noContent().build();
    }
    
}
