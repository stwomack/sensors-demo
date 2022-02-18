package org.tanzu.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SensorsUiController {

    private final SensorRepository sensorRepository;

    @Value("${title}")
    private String title;

    public SensorsUiController(final SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping
    public String fetchUI(Model model) {
        var formattedSensorData = sensorRepository.findAll()
                .stream().map(s -> new SensorData(
                                s.getId(),
                                Math.round(s.getTemperature() * 100) / 100.0d,
                                Math.round(s.getPressure() * 100) / 100.0d
                        )
                ).collect(java.util.stream.Collectors.toList());
        model.addAttribute("sensors", formattedSensorData);
        model.addAttribute("title", title);
        return "index";
    }
}

