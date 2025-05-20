package pl.edu.pjatk.s25819.smartcity.dto.auth;

public record LoginRequestDto(
        String username,
        String password
) {
}
