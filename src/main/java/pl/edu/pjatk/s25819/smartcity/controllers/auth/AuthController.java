package pl.edu.pjatk.s25819.smartcity.controllers.auth;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.s25819.smartcity.dtos.auth.*;
import pl.edu.pjatk.s25819.smartcity.services.profiles.ProfileService;

import java.util.Map;


@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto, HttpServletRequest request) {

        if (profileService.existsByUsername(registerRequestDto.username())) {
            throw new IllegalArgumentException("Użytkownik o podanej nazwie już istnieje");
        }

        profileService.saveProfile(
                RegisterMapper.toEntity(registerRequestDto)
        );

        return ResponseEntity.ok(new RegisterResponseDto(
                registerRequestDto.username(),
                registerRequestDto.email()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {

        if (loginRequestDto.username() == null || loginRequestDto.username().isEmpty()) {
            throw new IllegalArgumentException("Nie podano nazwy użytkownika");
        }

        Map<String, String> authenticated = null;

        try {
            authenticated = profileService.authenticate(loginRequestDto.username(), loginRequestDto.password());
        } catch (Exception e) {
            throw new IllegalArgumentException("Niepoprawne dane logowania");
        }
        return ResponseEntity.ok(new LoginResponseDto(authenticated.get("accessToken"), authenticated.get("refreshToken")));
    }

    @PutMapping("/roles")
    public ResponseEntity<AddRoleResponseDto> addRole(@RequestBody AddRoleRequestDto addRoleRequestDto, HttpServletRequest request) {

        if (addRoleRequestDto.username() == null || addRoleRequestDto.username().isEmpty()) {
            throw new IllegalArgumentException("Nie podano nazwy użytkownika");
        }

        if (addRoleRequestDto.roles() == null || addRoleRequestDto.roles().isEmpty()) {
            throw new IllegalArgumentException("Nie podano roli");
        }

        for (String role : addRoleRequestDto.roles()) {
            profileService.addRoleToProfile(addRoleRequestDto.username(), role);
        }

        return ResponseEntity.ok(new AddRoleResponseDto(
                addRoleRequestDto.username(),
                addRoleRequestDto.roles()
        ));
    }
}
