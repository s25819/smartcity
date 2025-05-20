package pl.edu.pjatk.s25819.smartcity.dto.auth;

import java.util.List;

public record AddRoleRequestDto(
        String username,
        List<String> roles
) {
}
