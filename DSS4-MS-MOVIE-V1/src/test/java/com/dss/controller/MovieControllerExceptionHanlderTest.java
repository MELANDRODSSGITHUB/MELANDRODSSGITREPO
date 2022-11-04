package com.dss.controller;

import com.dss.entity.Admin;
import com.dss.model.AdminRequest;
import com.dss.service.MovieService;
import com.dss.util.ResponseMsgConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(MovieController.class)
public class MovieControllerExceptionHanlderTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService))
                .setControllerAdvice(DSSExceptionHandler.class)
                .build();
    }
    @Test
    void shouldCreateMockMvc() {
        Assertions.assertNotNull(mockMvc);
    }

    /*@Test
    void shouldReturnListOfAdmin() throws Exception {
        Mockito.when(adminService.getAllAdmins()).thenReturn(List.of(Admin.builder()
                .adminId(1).firstName("Test_FIRST").lastName("TEST LAST").build()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/admin/getAll"))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void doLoginThrowLoginAuthenticationException() throws Exception {
        Mockito.when(adminService.doLogin(anyString(),anyString())).thenThrow( new LoginAuthenticationException(ResponseMsgConstant.LOGIN_AUTHENTICATION_FAILED_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/admin/test/test"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void addAdminThrowDuplicateAdminException() throws Exception {
        AdminRequest adminRequest = AdminRequest.builder().adminId(1).mobileNumber("09123456789")
                .password("TEST").firstName("TEST").lastName("TEST").email("test@gmail.com").build();
        Mockito.when(adminService.addAdmin(any(AdminRequest.class))).thenThrow( new DuplicateAdminException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/admin/register")).andExpect(MockMvcResultMatchers.status().isConflict());
    }
*/
}
