package com.example.webserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
}