package com.xzw.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class test {

    @RequestMapping(value = "/redirtUrl", method = RequestMethod.GET)
    public void redirtUrl(@RequestParam String token) {
        String url = "http://localhost:8081/link/gotoLoginClient";
    }

}
