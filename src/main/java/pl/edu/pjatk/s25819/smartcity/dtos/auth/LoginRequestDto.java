package pl.edu.pjatk.s25819.smartcity.dtos.auth;

public record LoginRequestDto(
        String username,
        String password
) {
}
