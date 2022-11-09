package com.dss.controller;

import com.dss.entity.Actor;
import com.dss.entity.Admin;
import com.dss.model.AdminRequest;
import com.dss.service.ActorService;
import com.dss.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/dss2/api/admin")
public class AdminController {
    private static final String GET_ALL_ADMINS_URL = "/getAll";
    private static final String DO_LOGIN_URL = "/{email}/{password}";
    private static final String REGISTER_ADMIN_URL = "/register";

    @Autowired
    AdminService adminService;

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping(value = REGISTER_ADMIN_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminRequest adminRequest) {
        return new ResponseEntity<>(adminService.addAdmin(adminRequest), HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_ADMINS_URL)
    public ResponseEntity<List<Admin>> getAllActor() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(adminService.getAllAdmins());
    }

    @GetMapping(DO_LOGIN_URL)
    public ResponseEntity<String> doLogin(@Valid @NotBlank(message = "Email is required") @PathVariable(value = "email") String email, @Valid @NotBlank(message = "Password is required") @PathVariable(value = "password") String password) {
        return new ResponseEntity<>(adminService.doLogin(email, password), HttpStatus.OK);
    }

}
