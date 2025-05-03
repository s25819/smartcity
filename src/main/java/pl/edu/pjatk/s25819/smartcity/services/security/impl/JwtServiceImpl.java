package pl.edu.pjatk.s25819.smartcity.services.security.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;
import pl.edu.pjatk.s25819.smartcity.services.security.JwtService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final String issuer;
    private final Duration expirationTime;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtServiceImpl(
            @Value("${spring.application.name}") String issuer,
            @Value("${security.jwt.expiration}") Duration expirationTime,
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder) {
        this.issuer = issuer;
        this.expirationTime = expirationTime;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public String generateAccessToken(String username, Map<String, Object> claims) {
        try {
            if (claims.containsKey("roles")) {
                Object rolesObj = claims.get("roles");
                List<String> roleNames = extractRoleNames(rolesObj);
                if (roleNames != null) {
                    claims.put("roles", roleNames);
                }
            }

            final var claimsSet = JwtClaimsSet.builder()
                    .claims(c -> c.putAll(claims))
                    .subject(username)
                    .issuer(issuer)
                    .expiresAt(Instant.now().plus(expirationTime))
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
        } catch (Exception e) {
            log.error("JWT ENCODING ERROR", e);
            throw e;
        }
    }

    /**
     * Metoda ekstrakcji nazw ról z różnych typów obiektów
     */
    private List<String> extractRoleNames(Object rolesObj) {
        if (rolesObj == null) {
            return List.of();
        }

        if (rolesObj instanceof List) {
            List<?> rolesList = (List<?>) rolesObj;
            if (rolesList.isEmpty()) {
                return List.of();
            }

            Object firstElement = rolesList.get(0);

            if (firstElement instanceof Role) {
                @SuppressWarnings("unchecked")
                List<Role> roles = (List<Role>) rolesList;
                return roles.stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toList());
            } else if (firstElement instanceof GrantedAuthority) {
                @SuppressWarnings("unchecked")
                List<GrantedAuthority> authorities = (List<GrantedAuthority>) rolesList;
                return authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
            } else if (firstElement instanceof String) {
                @SuppressWarnings("unchecked")
                List<String> roleNames = (List<String>) rolesList;
                return roleNames;
            }
        } else if (rolesObj instanceof String) {
            return List.of((String) rolesObj);
        } else if (rolesObj instanceof Role) {
            return List.of(((Role) rolesObj).getRoleName());
        } else if (rolesObj instanceof GrantedAuthority) {
            return List.of(((GrantedAuthority) rolesObj).getAuthority());
        }

        log.warn("Nieznany format ról: {}", rolesObj.getClass().getName());
        return List.of();
    }

    @Override
    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(expirationTime.multipliedBy(2)))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String extractUsername(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    @Override
    public boolean isTokenExpired(String token) {
        Instant expiration = jwtDecoder.decode(token).getExpiresAt();
        return expiration != null && expiration.isBefore(Instant.now());
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        try {
            final String tokenUsername = extractUsername(token);
            return tokenUsername.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Error validating token", e);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> extractRole(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            if (jwt.getClaims().containsKey("roles")) {
                List<String> roleStrings = (List<String>) jwt.getClaim("roles");
                return roleStrings.stream()
                        .map(roleName -> {
                            Role role = new Role();
                            role.setRoleName(roleName);
                            return role;
                        })
                        .collect(Collectors.toList());
            }
            return List.of();
        } catch (Exception e) {
            log.error("Error extracting roles from token", e);
            return List.of();
        }
    }
}

