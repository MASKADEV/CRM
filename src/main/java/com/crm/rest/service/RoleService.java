package com.crm.rest.service;

import com.crm.rest.exception.ResourceNotFoundException;
import com.crm.rest.model.ERole;
import com.crm.rest.model.Role;
import com.crm.rest.repository.RoleRepository;
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
