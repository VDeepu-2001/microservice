package com.RestAPI.microservice.controllers;

import com.RestAPI.microservice.services.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/microservice")
public class MicroserviceController {
    private final ApiService apiService;

    public MicroserviceController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/hello")
    public String printLog() {
        System.out.println("Hello World");
        System.out.println("welcome");
        return "Hello World!!!";
    }

    @GetMapping("/api/serviceCategory")
    public String getServiceCategory() {
        System.out.println("API execution started");
        int offSet = 1;
        int limit = 1;
        String result = apiService.fetchServiceCategory(offSet, limit);

        System.out.println("API execution done");

        return "Got the response from external open API endpoint";
    }

}
