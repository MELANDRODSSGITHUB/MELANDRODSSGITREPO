package com.dss.controller;

import com.dss.entity.User;
import com.dss.model.UserRequest;
import com.dss.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dss/api/admin")
public class AdminController {
    private static final String GET_ALL_ADMINS_URL = "/getAll";
    private static final String DO_LOGIN_URL = "/login";
    private static final String REGISTER_ADMIN_URL = "/register";

    @Autowired
    AdminService adminService;

    @Autowired
    private Environment environment;

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostMapping(REGISTER_ADMIN_URL)
    public ResponseEntity<String> registerAdmin(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(adminService.addAdmin(userRequest), HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_ADMINS_URL)
    public ResponseEntity<List<User>> getAllActor() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(adminService.getAllAdmins());
    }

    @GetMapping("/instance")
    public String getInstancePort() {
        return environment.getProperty("local.server.port");
    }

}
