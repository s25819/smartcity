package pl.edu.pjatk.s25819.smartcity.controllers.sensors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.s25819.smartcity.dto.sensors.SensorResponseDto;
import pl.edu.pjatk.s25819.smartcity.services.sensors.SensorsService;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
@Slf4j
public class SensorsController {

    private final SensorsService sensorsService;

    @GetMapping
    public ResponseEntity<List<SensorResponseDto>> getAllSensors() {
        log.info("Pobieram dane o wszystkich sensorach");

        return ResponseEntity.ok(sensorsService.getAllSensors());
    }
}
