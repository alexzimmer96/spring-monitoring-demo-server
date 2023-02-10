package com.mtitsolutions.training.monitoring.monitoringdemoservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExampleService {
    public boolean isHappy() {
        log.info("handled call in business logic layer");
        return true;
    }
}
