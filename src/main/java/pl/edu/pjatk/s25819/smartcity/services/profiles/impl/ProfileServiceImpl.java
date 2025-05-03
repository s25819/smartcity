package pl.edu.pjatk.s25819.smartcity.services.profiles.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;
import pl.edu.pjatk.s25819.smartcity.repositories.profiles.ProfileRepository;
import pl.edu.pjatk.s25819.smartcity.repositories.profiles.RoleRepository;
import pl.edu.pjatk.s25819.smartcity.services.profiles.ProfileService;
import pl.edu.pjatk.s25819.smartcity.services.security.JwtService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Profile getProfileById(Long id) {
        log.info("Pobieranie profilu z: {}", id);

        return profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Profil nie został znaleziony"));
    }

    @Override
    public Boolean existsByUsername(String username) {
        log.info("Sprawdzanie istnienia profilu z: {}", username);

        if (username == null || username.isEmpty()) {
            log.error("Nie podano nazwy użytkownika");
            throw new IllegalArgumentException("Nie podano nazwy użytkownika");
        }

        boolean exists = profileRepository.existsByUsername(username);
        if (exists) {
            log.info("Profil {} istnieje", username);
        } else {
            log.info("Profil {} nie istnieje", username);
        }

        return exists;
    }

    @Override
    public Profile getProfileByUsername(String username) {
        log.info("Pobieranie profilu z: {}", username);

        if (username == null || username.isEmpty()) {
            log.error("Nie podano nazwy użytkownika");
            throw new IllegalArgumentException("Nie podano nazwy użytkownika");
        }

        var profile = profileRepository.findByUsername(username);
        if (profile.isEmpty()) {
            log.error("Nie znaleziono profilu {}", username);
            throw new IllegalArgumentException("Nie znaleziono profilu");
        }

        return profile.get();
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile saveProfile(Profile profile) {

        var encodedPassword = passwordEncoder.encode(profile.getPassword());

        profile.setPassword(encodedPassword);

        return profileRepository.save(profile);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToProfile(String username, String rolename) {

        log.info("Dodawanie roli {} do profilu {}", rolename, username);
        Profile profile = null;
        Role role = null;

        try {
            role = roleRepository.findByRoleName(rolename);
            profile = getProfileByUsername(username);
        } catch (Exception e) {

            log.error("Wystąpił błąd podczas dodawania roli {} do profilu {}", rolename, username);
            throw new IllegalArgumentException("Wystąpił błąd podczas dodawania roli do profilu");
        }

        if (profile == null) {
            log.error("Nie znaleziono profilu {}", username);
            throw new IllegalArgumentException("Nie znaleziono profilu");
        }

        if (role == null) {
            log.error("Nie znaleziono roli {}", rolename);
            throw new IllegalArgumentException("Nie znaleziono roli");
        }

        if (profile.getRoles().contains(role)) {
            log.info("Profil {} ma już rolę {}", username, rolename);
        } else {
            profile.getRoles().add(role);
            profileRepository.save(profile);
            log.info("Dodano rolę {} do profilu {}", rolename, username);
        }
    }

    @Override
    public Boolean isPasswordValid(String username, String password) {
        var profile = getProfileByUsername(username);
        if (profile == null) {
            log.error("Nie znaleziono profilu {}", username);
            throw new IllegalArgumentException("Nie znaleziono profilu");
        }

        if (passwordEncoder.matches(password, profile.getPassword())) {
            log.info("Hasło dla profilu {} jest poprawne", username);
            return true;
        } else {
            log.error("Hasło dla profilu {} jest niepoprawne", username);
            throw new IllegalArgumentException("Błąd logowania użytkownika");
        }
    }

    @Override
    public Map<String, String> authenticate(String username, String password) {
        final var authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        final var authentication = authenticationManager.authenticate(authenticationToken);

        final var claims = new HashMap<String, Object>();
        claims.put("roles", authentication.getAuthorities());

        final var accessToken = jwtService.generateAccessToken(username, claims);
        final var refreshToken = jwtService.generateRefreshToken(username);

        return new HashMap<>() {{
            put("accessToken", accessToken);
            put("refreshToken", refreshToken);
        }};
    }
}
