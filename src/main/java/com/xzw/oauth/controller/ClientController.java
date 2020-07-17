package com.xzw.oauth.controller;

import com.xzw.oauth.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @RequestMapping("/getClientDetail")
    public List<String> getClientDetail() {
        List<String> clientList = new ArrayList<>();
        clientList.add("合同管理");
        clientList.add("青少年运动中心");
        return clientList;
    }

    @RequestMapping("/getClientDetail1")
    public List<String> getClientDetail1() {
        List<String> clientList = new ArrayList<>();
        clientList.add("合同管理");
        clientList.add("青少年运动中心");
        return clientList;
    }

    @RequestMapping(value = "/tiaozhuan", method = RequestMethod.GET)
    public void tiaozhuan(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = request.getParameter("token");
        String body = HttpUtils.getData("http://192.168.1.120:8081/link/gotoLoginClient", token);//body为获取的html代码
        Document doc = Jsoup.parse(body);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(doc.outerHtml());


    }




}
