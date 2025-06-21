package com.example.advisor_backend.controller;

import com.example.advisor_backend.bean.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Strategy_Manage {
    @RequestMapping("/test")
    public String login(@RequestBody User user)
    {
        System.out.println(user);
        return "OK1";
    }

    @RequestMapping("/strategy_manage")
    public String test(){
        return "Only Test";
    }
}
