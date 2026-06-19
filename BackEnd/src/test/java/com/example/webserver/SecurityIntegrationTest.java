package com.example.webserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectProfileWithoutToken() throws Exception {

        mockMvc.perform(
                get("/api/user/profile")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(
        username = "testuser",
        roles = {"USER"}
    )
    void shouldDenyAdminEndpointForUserRole() throws Exception {

    mockMvc.perform(
            get("/api/admin/users")
    )
    .andExpect(status().isForbidden());
}

    @Test
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN"}
        )
        void shouldAllowAdminEndpointForAdminRole() throws Exception {
            
            mockMvc.perform(
                get("/api/admin/users")
            )
            .andExpect(status().isOk());   
    }
}