package com.dss.service;

import com.dss.entity.User;
import com.dss.exception.DuplicateAdminException;
import com.dss.exception.EmailAlreadyBeenUsedException;
import com.dss.exception.LoginAuthenticationException;
import com.dss.model.UserRequest;
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
public class UserServiceTest {

    @Mock
    private AdminRepository adminRepository;

    private AdminService adminService;

    private UserRequest userRequest;
    private User user;
    @BeforeEach
    void setUp() {

        this.adminService = new AdminServiceImpl(this.adminRepository);
        userRequest = UserRequest.builder()
                .adminId(1)
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .email("test@gmail.com")
                .password("P@ssw0rd")
                .mobileNumber("09179224585").build();
        user = User.builder()
                .userId(1)
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
        Mockito.when(adminRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));
        Assertions.assertThrows(DuplicateAdminException.class,() -> adminService.addAdmin(userRequest));
    }

    @Test
    void addAdminThrowEmailAlreadyBeenUsedException() {
        Mockito.when(adminRepository.findAdminByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        Assertions.assertThrows(EmailAlreadyBeenUsedException.class,() -> adminService.addAdmin(userRequest));
    }

    @Test
    void addAdminSuccess(){
        Mockito.when(adminRepository.save(any(User.class))).thenReturn(user);
        Assertions.assertEquals(adminService.addAdmin(userRequest),
                ResponseMsgConstant.SUCCESSFULLY_REGISTER_ADMIN_MSG.concat(" (id: " + user.getUserId()
                        + "\t Name: " + user.getFirstName() + " "
                        + user.getLastName() + ")"));
    }

/*    @Test
    void doLoginThrowLoginAuthenticationException() {
        Mockito.when(adminRepository.findAdminByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(LoginAuthenticationException.class,() -> adminService.doLogin(userRequest));
    }

    @Test
    void doLoginSuccess() {
        user.setPassword(DSSUtil.passwordEncryption(user.getPassword()));
        Optional<User> adminOptional = Optional.ofNullable(user);
        Mockito.when(adminRepository.findAdminByEmailAndPassword(anyString(), anyString())).thenReturn(adminOptional);
        Assertions.assertEquals(ResponseMsgConstant.LOGIN_AUTHENTICATION_SUCCESS_MSG, adminService.doLogin(user.getEmail(), user.getPassword()));
    }*/
}
