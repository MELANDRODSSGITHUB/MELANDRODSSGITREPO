package com.dss.controller;

import com.dss.entity.Admin;
import com.dss.exception.DuplicateAdminException;
import com.dss.exception.EmailAlreadyBeenUsedException;
import com.dss.exception.LoginAuthenticationException;
import com.dss.model.AdminRequest;
import com.dss.service.AdminService;
import com.dss.util.ResponseMsgConstant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @MockBean
    private AdminService adminService;
    private Admin admin;
    private AdminRequest adminRequest;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    
    ObjectWriter ow;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController();
        adminController.setAdminService(adminService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setControllerAdvice(DSSExceptionHandler.class)
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = objectMapper.writer().withDefaultPrettyPrinter();

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
     void getAllAdmins() throws Exception {
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        Mockito.when(adminService.getAllAdmins()).thenReturn(adminList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/admin/getAll"))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void registerAdmin() throws Exception {
        String requestJson = ow.writeValueAsString(adminRequest);
        Mockito.when(adminService.addAdmin(any(AdminRequest.class))).thenReturn(anyString());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void registerAdminThrowRuntimeException() throws Exception {
        String requestJson = ow.writeValueAsString(adminRequest);
        Mockito.when(adminService.addAdmin(any(AdminRequest.class))).thenThrow(new RuntimeException());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

/*    @Test
    void registerAdminThrowPasswordEncryptionException() throws Exception {
        String requestJson = ow.writeValueAsString(adminRequest);
        Mockito.when(adminService.addAdmin(any(AdminRequest.class))).thenThrow(new PasswordEncryptionException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }*/

    @Test
    void registerAdminThrowMethodArgumentNotValidException() throws Exception {
        AdminRequest badAdminRequest = adminRequest;
        badAdminRequest.setFirstName("");
        badAdminRequest.setLastName("");
        String requestJson = ow.writeValueAsString(badAdminRequest);
       MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        Assertions.assertNotNull(result.getResolvedException());
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
        Map<String, Object> map = objectMapper.readValue(content, new TypeReference<>() {});
        Assertions.assertNotNull(map.get("errors"));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    void addAdminThrowDuplicateAdminException() throws Exception {
        String requestJson = ow.writeValueAsString(adminRequest);
        Mockito.when(adminService.addAdmin(any(AdminRequest.class))).thenThrow(new DuplicateAdminException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void addAdminThrowEmailAlreadyBeenUsedException() throws Exception {
        String requestJson = ow.writeValueAsString(adminRequest);
        Mockito.when(adminService.addAdmin(any(AdminRequest.class))).thenThrow(new EmailAlreadyBeenUsedException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
     void doLogin() throws Exception {
        Mockito.when(adminService.doLogin(anyString(), anyString())).thenReturn(ResponseMsgConstant.LOGIN_AUTHENTICATION_SUCCESS_MSG);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/admin/test/test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void doLoginThrowLoginAuthenticationException() throws Exception {
        Mockito.when(adminService.doLogin(anyString(), anyString())).thenThrow(new LoginAuthenticationException(ResponseMsgConstant.LOGIN_AUTHENTICATION_FAILED_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/admin/test/test"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
