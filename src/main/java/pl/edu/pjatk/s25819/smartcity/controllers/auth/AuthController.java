package pl.edu.pjatk.s25819.smartcity.controllers.auth;


import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.s25819.smartcity.dto.auth.*;
import pl.edu.pjatk.s25819.smartcity.services.profiles.ProfileService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto, HttpServletRequest request) throws AuthException {

        if (profileService.existsByUsername(registerRequestDto.username())) {
            throw new AuthException("Użytkownik o podanej nazwie już istnieje");
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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) throws AuthException {

        if (loginRequestDto.username() == null || loginRequestDto.username().isEmpty()) {
            throw new AuthException("Nie podano nazwy użytkownika");
        }

        Map<String, String> authenticated = null;

        try {
            authenticated = profileService.authenticate(loginRequestDto.username(), loginRequestDto.password());
        } catch (Exception e) {
            throw new AuthException("Niepoprawne dane logowania");
        }
        return ResponseEntity.ok(new LoginResponseDto(authenticated.get("accessToken"), authenticated.get("refreshToken")));
    }

    @PutMapping("/roles")
    public ResponseEntity<AddRoleResponseDto> addRole(@RequestBody AddRoleRequestDto addRoleRequestDto, HttpServletRequest request) throws AuthException {

        if (addRoleRequestDto.username() == null || addRoleRequestDto.username().isEmpty()) {
            throw new AuthException("Nie podano nazwy użytkownika");
        }

        if (addRoleRequestDto.roles() == null || addRoleRequestDto.roles().isEmpty()) {
            throw new AuthException("Nie podano roli");
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
