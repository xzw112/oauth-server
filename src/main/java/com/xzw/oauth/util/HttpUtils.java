package com.xzw.oauth.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static Logger Log  = LoggerFactory.getLogger(HttpUtils.class);


    // 1.使用get方式发送报文
    public static String getData(String url, String token) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + token);
        try{
            CloseableHttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                Log.info("远程调用成功.line={}",response.getStatusLine());
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity,"UTF-8");
            }
            return  null;
        }catch (IOException e){
            Log.error("远程调用失败.e={}",e.getMessage());
        }
        return  null;
    }
    public static String post(String type,String url,String data){
        String result = "";
        switch (type){
            case "xml":
                result =  postData(url,data, ContentType.APPLICATION_XML.toString());
                break;
            case "json":
                result = postData(url,data,ContentType.APPLICATION_JSON.toString());
                break;
            default:
                break;
        }
        return  result;
    }


    // 使用POST方法发送XML或者json数据
    public static String postData(String url, String xmlData,String contentType){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type",contentType);
        try{
            StringEntity entity = new StringEntity(xmlData);
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                Log.info("远程调用成功.line={}",response.getStatusLine());
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity);
            }
        }catch (IOException e){
            Log.error("远程调用失败.e={}",e.getMessage());
        }
        return  null;
    }

    // 使用POST方法发送FORM表单数据
    public static String postForm(String url, Map<String,String> map){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type",ContentType.APPLICATION_FORM_URLENCODED.toString());
        try{
            List<BasicNameValuePair> list = new ArrayList<>();
            for(Map.Entry<String,String> entry : map.entrySet()){
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
            post.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                Log.info("远程调用成功.line={}",response.getStatusLine());
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity);
            }
        }catch (IOException e){
            Log.error("远程调用失败.e={}",e.getMessage());
        }
        return  null;
    }
}
