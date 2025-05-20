package pl.edu.pjatk.s25819.smartcity.dto.auth;

import java.util.List;

public record AddRoleResponseDto(
        String username,
        List<String> role
) {
}
