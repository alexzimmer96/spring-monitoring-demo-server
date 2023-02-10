package com.mtitsolutions.training.monitoring.monitoringdemoservice.controller;

import com.mtitsolutions.training.monitoring.monitoringdemoservice.service.ExampleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api")
@Slf4j
@RequiredArgsConstructor
public class DemoController {

    private final ExampleService exampleService;

    @GetMapping("happy")
    public ResponseEntity<String> getHappy() {
        this.exampleService.isHappy();
        log.info("successfully handled happy-path request");
        return ResponseEntity.ok("You chose the happy path - Nice :)");
    }

    @GetMapping("unhappy")
    public ResponseEntity<String> getUnhappy() {
        this.exampleService.isHappy();
        log.info("error occurred while handling unhappy path");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Oh no, you chose the unhappy path :(");
    }

    @GetMapping("long-running")
    public ResponseEntity<String> getLongRunning() throws InterruptedException {
        this.exampleService.isHappy();
        Thread.sleep((long)(Math.random() * 1000));
        log.info("successfully handlet long-running task request");
        return ResponseEntity.ok("I hope you did not wait to long");
    }

    @GetMapping("exception")
    public ResponseEntity<String> getException() {
        this.exampleService.isHappy();
        throw new RuntimeException();
    }

}
