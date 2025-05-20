package pl.edu.pjatk.s25819.smartcity.controllers;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pjatk.s25819.smartcity.dto.GenericErrorResponseDto;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<GenericErrorResponseDto> handleAuthException(AuthException exception,
            HttpServletRequest request) {
        GenericErrorResponseDto errorResponse = new GenericErrorResponseDto(
                getPrintStackTrace(exception),
                exception.getClass().getSimpleName(),
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                System.currentTimeMillis());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorResponseDto> handleGenericException(Exception exception,
            HttpServletRequest request) {
        GenericErrorResponseDto errorResponse = new GenericErrorResponseDto(
                getPrintStackTrace(exception),
                exception.getClass().getSimpleName(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    private String getPrintStackTrace(Exception exception) {

        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
