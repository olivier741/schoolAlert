/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Product;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author olivier.tatsinkou
 */
@Service
public class RegisterService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${application.register.url}")
    private String URL;

    public String getRegisterStatus(String serviceName, String msisdn) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        JSONObject jsonObject = new JSONObject();
        try {
            
        } catch (Exception e) {
        }
        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        return restTemplate.exchange(URL, HttpMethod.GET, entity, String.class).getBody();
    }
}
