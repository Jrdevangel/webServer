package com.example.webserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAllowAccessToUsersEndpoint()
            throws Exception {
        mockMvc.perform(
                get("/api/admin/users")
        )
        .andExpect(status().isOk());
    }

        @Test
        @WithMockUser(roles = "USER")
        void shouldDenyAccessToUsersEndpointForNonAdmin()
                throws Exception {
            mockMvc.perform(
                    get("/api/admin/users")
            )
            .andExpect(status().isForbidden());
        }
}
