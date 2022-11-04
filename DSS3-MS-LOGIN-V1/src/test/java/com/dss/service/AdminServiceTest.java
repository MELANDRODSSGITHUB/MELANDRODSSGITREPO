package com.dss.service;

import com.dss.entity.Admin;
import com.dss.exception.DuplicateAdminException;
import com.dss.exception.EmailAlreadyBeenUsedException;
import com.dss.exception.LoginAuthenticationException;
import com.dss.model.AdminRequest;
import com.dss.repository.AdminRepository;
import com.dss.util.DSSUtil;
import com.dss.util.ResponseMsgConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    private AdminService adminService;

    private AdminRequest adminRequest;
    private Admin admin;
    @BeforeEach
    void setUp() {

        this.adminService = new AdminServiceImpl(this.adminRepository);
        adminRequest = AdminRequest.builder()
                .adminId(1)
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .email("test@gmail.com")
                .password("P@ssw0rd")
                .mobileNumber("09179224585").build();
        admin = Admin.builder()
                .adminId(1)
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .email("test@gmail.com")
                .password("P@ssw0rd")
                .mobileNumber("09179224585").build();
    }

    @Test
    void getAllAdmins() {
        adminService.getAllAdmins();
        verify(adminRepository).findAll();
    }

    @Test
    void addAdminThrowDuplicateAdminException() {
        Mockito.when(adminRepository.findById(anyInt())).thenReturn(Optional.ofNullable(admin));
        Assertions.assertThrows(DuplicateAdminException.class,() -> adminService.addAdmin(adminRequest));
    }

    @Test
    void addAdminThrowEmailAlreadyBeenUsedException() {
        Mockito.when(adminRepository.findAdminByEmail(anyString())).thenReturn(Optional.ofNullable(admin));
        Assertions.assertThrows(EmailAlreadyBeenUsedException.class,() -> adminService.addAdmin(adminRequest));
    }

    @Test
    void addAdminSuccess(){
        Mockito.when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        Assertions.assertEquals(adminService.addAdmin(adminRequest),
                ResponseMsgConstant.SUCCESSFULLY_REGISTER_ADMIN_MSG.concat(" (id: " + admin.getAdminId()
                        + "\t Name: " + admin.getFirstName() + " "
                        + admin.getLastName() + ")"));
    }

    @Test
    void doLoginThrowLoginAuthenticationException() {
        Mockito.when(adminRepository.findAdminByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(LoginAuthenticationException.class,() -> adminService.doLogin(admin.getEmail(),admin.getPassword()));
    }

    @Test
    void doLoginSuccess() {
        admin.setPassword(DSSUtil.passwordEncryption(admin.getPassword()));
        Optional<Admin> adminOptional = Optional.ofNullable(admin);
        Mockito.when(adminRepository.findAdminByEmailAndPassword(anyString(), anyString())).thenReturn(adminOptional);
        Assertions.assertEquals(ResponseMsgConstant.LOGIN_AUTHENTICATION_SUCCESS_MSG, adminService.doLogin(admin.getEmail(),admin.getPassword()));
    }
}
