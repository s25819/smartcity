package pl.edu.pjatk.s25819.smartcity.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pjatk.s25819.smartcity.dtos.auth.GenericErrorResponseDto;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorResponseDto> handleGenericException(Exception exception, HttpServletRequest request) {
        GenericErrorResponseDto errorResponse = new GenericErrorResponseDto(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis()
        );

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
