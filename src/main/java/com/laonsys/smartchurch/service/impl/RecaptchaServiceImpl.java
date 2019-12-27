package com.laonsys.smartchurch.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.laonsys.smartchurch.service.RecaptchaService;

@Service("recaptchaService")
public class RecaptchaServiceImpl implements RecaptchaService {
    
    private @Value("#{envvars['recaptcha.url']}") String url;
    private @Value("#{envvars['recaptcha.private']}") String privateKey;
    
    @Override
    public boolean verify(String remoteip, String challenge, String response) {
        RestTemplate template = new RestTemplate();
        
        MultiValueMap<String, Object> variablesMap = new LinkedMultiValueMap<String, Object>();
        variablesMap.add("privatekey", privateKey);
        variablesMap.add("remoteip", remoteip);
        variablesMap.add("challenge", challenge);
        variablesMap.add("response", response);
        
        String result = template.postForObject(url, variablesMap, String.class);
        String[] results = result.split("\n");
        
        return Boolean.parseBoolean(results[0]);
    }

}
