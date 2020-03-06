package com.kravchenko.ahp.controller;

import com.kravchenko.ahp.models.AHPRequest;
import com.kravchenko.ahp.service.AHPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AHPController {

    @Autowired
    private AHPService service;

    @PostMapping("/ahp")
    public Mono<Double[]> ahp(@RequestBody AHPRequest request) {
        return Mono.just(service.solveAHP(request));
    }

}
