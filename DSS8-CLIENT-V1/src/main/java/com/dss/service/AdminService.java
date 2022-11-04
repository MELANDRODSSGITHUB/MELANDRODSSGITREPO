package com.dss.service;

import com.dss.proxy.AdminProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminProxy adminProxy;

    public ResponseEntity<String> doLogin(String email, String password){
        return adminProxy.doLogin(email,password);
    }
    public String getServiceInstance() { return adminProxy.getServiceInstance();}
}
