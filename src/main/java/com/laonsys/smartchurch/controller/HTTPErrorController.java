package com.laonsys.smartchurch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTTPErrorController {

    @RequestMapping(value = "/errors/{code}")
    public String handleError(@PathVariable String code) {
        return "/error";
    }
}
