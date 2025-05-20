package pl.edu.pjatk.s25819.smartcity.services.sensors;

import pl.edu.pjatk.s25819.smartcity.dto.sensors.SensorResponseDto;

import java.util.List;

public interface SensorsService {

    List<SensorResponseDto> getAllSensors();

}
