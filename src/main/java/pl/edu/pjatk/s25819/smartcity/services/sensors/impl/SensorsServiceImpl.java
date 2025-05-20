package pl.edu.pjatk.s25819.smartcity.services.sensors.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.edu.pjatk.s25819.smartcity.dto.sensors.SensorResponseDto;
import pl.edu.pjatk.s25819.smartcity.services.sensors.SensorsService;

import java.util.List;

@Service
@Slf4j
public class SensorsServiceImpl implements SensorsService {

    private final WebClient sensorsWebClient;

    public SensorsServiceImpl(WebClient sensorsWebClient) {
        this.sensorsWebClient = sensorsWebClient;
    }

    @Override
    public List<SensorResponseDto> getAllSensors() {

        log.info("Próba pobrania danych o sensorach");

        try {

            var response = sensorsWebClient.get()
                    .uri("/sensors")
                    .retrieve().bodyToMono(new ParameterizedTypeReference<List<SensorResponseDto>>() {
                    });

            log.info("Zwrócono dane o sensorach: {}", response.block());

            return response.block() != null ? response.block() : List.of();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Błąd podczas pobierania danych o sensorach: {}", e.getMessage());
            throw new RuntimeException("Błąd podczas pobierania danych o sensorach", e);
        }
    }
}
