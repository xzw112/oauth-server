package com.xzw.oauth.model;

import lombok.Data;

import java.util.Map;

@Data
public class UserWebInfo {

    private String userName;

    private String userNumber;

    private Map<String,String> anuisMap;
}
