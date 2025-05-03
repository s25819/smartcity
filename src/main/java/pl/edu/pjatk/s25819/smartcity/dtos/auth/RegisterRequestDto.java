package pl.edu.pjatk.s25819.smartcity.dtos.auth;

public record RegisterRequestDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {
}
