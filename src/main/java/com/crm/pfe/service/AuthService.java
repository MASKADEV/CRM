package com.crm.pfe.service;

import com.crm.pfe.exception.TokenRefreshException;
import com.crm.pfe.exception.UserAlreadyExistsException;
import com.crm.pfe.model.ERole;
import com.crm.pfe.model.RefreshToken;
import com.crm.pfe.model.Role;
import com.crm.pfe.model.User;
import com.crm.pfe.payload.UserMachineDetails;
import com.crm.pfe.payload.request.LoginRequest;
import com.crm.pfe.payload.request.SignupRequest;
import com.crm.pfe.payload.request.TokenRefreshRequest;
import com.crm.pfe.payload.response.JwtResponse;
import com.crm.pfe.payload.response.TokenRefreshResponse;
import com.crm.pfe.repository.UserRepository;
import com.crm.pfe.security.jwt.JwtUtils;
import com.crm.pfe.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleService roleService;
    final PasswordEncoder passwordEncoder;
    final JwtUtils jwtUtils;
    final RefreshTokenService refreshTokenService;

    public JwtResponse signIn(LoginRequest loginRequest, UserMachineDetails userMachineDetails) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.create(userDetails.getId(), userMachineDetails);

        return JwtResponse
                .builder()
                .id(userDetails.getId().toString())
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .build();
    }

    public User signUp(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(signUpRequest.getEmail());
        }

        Set<Role> roles = Set.of(roleService.findByName(ERole.ROLE_USER));

        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map((user) -> {
                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    public void logout(UUID userId) {
        refreshTokenService.deleteByUserId(userId);
    }

}
