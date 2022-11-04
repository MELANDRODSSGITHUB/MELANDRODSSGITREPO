package com.dss.controller;

import com.dss.entity.Admin;
import com.dss.model.AdminRequest;
import com.dss.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/dss/api/admin")
public class AdminController {
    private static final String GET_ALL_ADMINS_URL = "/getAll";
    private static final String DO_LOGIN_URL = "/{email}/{password}";
    private static final String REGISTER_ADMIN_URL = "/register";

    @Autowired
    AdminService adminService;

    @Autowired
    private Environment environment;

    @PostMapping(REGISTER_ADMIN_URL)
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminRequest adminRequest) {
        return new ResponseEntity<>(adminService.addAdmin(adminRequest), HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_ADMINS_URL)
    public ResponseEntity<List<Admin>> getAllActor() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(adminService.getAllAdmins());
    }

    @GetMapping(DO_LOGIN_URL)
    public ResponseEntity<String> doLogin( @PathVariable String email, @PathVariable String password) {
        return new ResponseEntity<>(adminService.doLogin(email, password), HttpStatus.OK);
    }

    @GetMapping("/instance")
    public String getInstancePort() {
        return environment.getProperty("local.server.port");
    }

}
