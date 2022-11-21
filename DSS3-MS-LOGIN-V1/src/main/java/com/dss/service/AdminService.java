package com.dss.service;

import com.dss.entity.User;
import com.dss.model.UserRequest;

import java.util.List;

public interface AdminService {
    String addAdmin(UserRequest userRequest);
    List<User> getAllAdmins();

/*    String doLogin(String email, String password);*/
}
