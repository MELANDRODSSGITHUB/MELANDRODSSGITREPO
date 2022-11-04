package com.dss.service;

import com.dss.entity.Admin;
import com.dss.model.AdminRequest;

import java.util.List;

public interface AdminService {
    String addAdmin(AdminRequest adminRequest);
    List<Admin> getAllAdmins();

    String doLogin(String email, String password);
}
