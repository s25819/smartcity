package pl.edu.pjatk.s25819.smartcity.dto;

public record GenericErrorResponseDto(
        String message,
        String error,
        String path,
        int status,
        long timestamp
) {
}
