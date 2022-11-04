package com.dss.controller;

import com.dss.entity.Admin;
import com.dss.model.AdminRequest;
import com.dss.service.AdminService;
import com.dss.util.ResponseMsgConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AdminControllerTest.class)
public class AdminControllerTest {
    @Mock
    private AdminService adminService;

    @Mock
    private Environment environment;

    @InjectMocks
    private AdminController adminController;

    private Admin admin;
    private AdminRequest adminRequest;

    @BeforeEach
    public void setUp() {
        admin = Admin.builder()
                .adminId(1)
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .email("test@gmail.com")
                .password("P@ssw0rd")
                .mobileNumber("09179224585")
                .reviewList(new ArrayList<>()).build();
        adminRequest = AdminRequest.builder()
                .adminId(1)
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .email("test@gmail.com")
                .password("P@ssw0rd")
                .mobileNumber("09179224585").build();

    }

    @Test
    public void getAllAdmins() {
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        Mockito.when(adminService.getAllAdmins()).thenReturn(adminList);
        ResponseEntity<List<Admin>> response = adminController.getAllActor();
        Assertions.assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    public void registerAdmin() {
        Mockito.when(adminService.addAdmin(adminRequest)).thenReturn(
                ResponseMsgConstant.SUCCESSFULLY_REGISTER_ADMIN_MSG.concat(" (id: " + adminRequest.getAdminId()
                        + "\t Name: " + adminRequest.getFirstName() + " "
                        + adminRequest.getLastName() + ")"));
        ResponseEntity<String> response = adminController.registerAdmin(adminRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void doLogin() {
        Mockito.when(adminService.doLogin(anyString(), anyString())).thenReturn(ResponseMsgConstant.LOGIN_AUTHENTICATION_SUCCESS_MSG);
        ResponseEntity<String> response = adminController.doLogin(admin.getEmail(), admin.getPassword());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getInstancePort() {
        when(environment.getProperty(anyString())).thenReturn("9301");
        Assertions.assertEquals(adminController.getInstancePort(), "9301");

    }
}
