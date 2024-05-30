package com.crm.pfe.service;

import com.crm.pfe.exception.ResourceNotFoundException;
import com.crm.pfe.model.ERole;
import com.crm.pfe.model.Role;
import com.crm.pfe.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    final RoleRepository roleRepository;

    public Role findByName(ERole name) throws ResourceNotFoundException {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
    }

}
