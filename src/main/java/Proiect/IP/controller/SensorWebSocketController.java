package Proiect.IP.controller;

import Proiect.IP.model.Sensor;
import Proiect.IP.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SensorWebSocketController {

    @Autowired
    private SensorService sensorService;

    @MessageMapping("/sensor")
    @SendTo("/topic/sensor")
    public Sensor sendSensorUpdate(String patientId) {
        return sensorService.getLatestSensorData(patientId);
    }
}