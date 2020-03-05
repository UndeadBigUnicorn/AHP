package com.kravchenko.ahp.controller;

import com.kravchenko.ahp.models.AHPRequest;
import com.kravchenko.ahp.service.AHPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AHPController {

    @Autowired
    private AHPService service;

    @PostMapping("/ahp")
    public Double[] ahp(@RequestBody AHPRequest request) {
        return service.solveAHP(request).getPriorities();
    }

}
