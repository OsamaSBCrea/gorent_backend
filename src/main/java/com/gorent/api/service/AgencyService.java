package com.gorent.api.service;

import com.gorent.api.dto.AgencyDTORegister;
import com.gorent.api.model.Agency;
import com.gorent.api.model.User;
import com.gorent.api.repository.AgencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public Page<Agency> getAllAgencies(Pageable pageable) {
        return this.agencyRepository.findAll(pageable);
    }

    public Agency getAgencyById(Long id) {
        return agencyRepository.findById(id).orElse(null);
    }

    public Agency createAgency(AgencyDTORegister agencyDTO, User user) {
        Agency agency = new Agency(agencyDTO);
        agency.setUser(user);
        return this.agencyRepository.save(agency);
    }

    public Agency updateAgency(Long id, Agency agency) {
        Optional<Agency> agencyOptional = agencyRepository.findById(id);

        if(agencyOptional.isPresent()) {
            Agency existingAgency = agencyOptional.get();
            existingAgency.setName(agency.getName());
            existingAgency.setPhoneNumber(agency.getPhoneNumber());
            return agencyRepository.save(existingAgency);
        }

        return null;
    }

    public void deleteAgency(Long id) {
        this.agencyRepository.deleteById(id);
    }

    public Page<Agency> searchAgencies(String search, Pageable pageable) {
        return this.agencyRepository.findByNameContainingIgnoreCase(search, pageable);
    }
    
}
