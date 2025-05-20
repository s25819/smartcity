package pl.edu.pjatk.s25819.smartcity.dto.auth;

import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;

import java.time.Instant;
import java.util.ArrayList;

public class RegisterMapper {

    public static Profile toEntity(RegisterRequestDto dto) {
        return new Profile(
                dto.firstName(),
                dto.lastName(),
                dto.username(),
                dto.password(),
                dto.email(),
                Instant.now(),
                new ArrayList<>()
        );
    }
}
