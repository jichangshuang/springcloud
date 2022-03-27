package com.chinasoft.sentinel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login() {
        System.out.println("登录成功了...");
        return "success";
    }
}
