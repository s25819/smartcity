package pl.edu.pjatk.s25819.smartcity.services.security;

import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;

import java.util.List;
import java.util.Map;

public interface JwtService {

    String generateAccessToken(String username, Map<String, Object> claims);

    String generateRefreshToken(String username);

    String extractUsername(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, String username);

    List<Role> extractRole(String token);
}
