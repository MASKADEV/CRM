package com.crm.rest.repository;

import com.crm.rest.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.crm.rest.util.role.RoleCreator.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Tests for RefreshTokenRepository")
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    @DisplayName("findByName Returns Role When Successful")
    void findByName_ReturnsRole_WhenSuccessful() {
        roleRepository.save(createRoleToBeSave());

        Optional<Role> roleFound = roleRepository.findByName(createRoleToBeSave().getName());

        assertThat(roleFound).isNotEmpty();

        assertThat(roleFound.get().getName()).isEqualTo(createRoleToBeSave().getName());
    }

}
