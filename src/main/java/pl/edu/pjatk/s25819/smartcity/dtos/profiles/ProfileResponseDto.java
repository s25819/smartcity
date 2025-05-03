package pl.edu.pjatk.s25819.smartcity.dtos.profiles;

import java.util.List;

public record ProfileResponseDto(
        String username,
        String email,
        String firstName,
        String lastName,
        List<String> roles
) {
}
