package com.crm.pfe.repository;

import com.crm.pfe.model.RefreshToken;
import com.crm.pfe.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    Page<RefreshToken> findAllByUser(Pageable pageable, User user);

    @Modifying
    void deleteByUser(User user);
}
