package com.example.advisor_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Strategy_Manage {
    @RequestMapping("/strategy_manage")
    public String test(){
        return "Only Test";
    }
}
