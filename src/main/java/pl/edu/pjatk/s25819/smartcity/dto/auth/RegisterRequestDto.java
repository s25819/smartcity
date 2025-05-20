package pl.edu.pjatk.s25819.smartcity.dto.auth;

public record RegisterRequestDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {
}
