package com.xzw.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/link")
public class IndexGoToController {

    @RequestMapping("/gotoLogin")
    public String gotoLogin() {
        return "login";
    }

    @RequestMapping("/gotoSuccess")
    public String gotoSuccess(HttpServletRequest request) {
        String token = request.getParameter("token");
        request.setAttribute("token", token);
        return "success";
    }
}
