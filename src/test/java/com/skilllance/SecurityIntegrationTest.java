package com.skilllance;

import com.skilllance.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Test
    void protectedEndpointShouldRejectUnauthenticatedRequest()
            throws Exception {

        mockMvc.perform(
                get("/api/projects")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointShouldAllowValidJwt()
            throws Exception {

        String token = jwtService.generateToken(
                "test@skilllance.com",
                "FREELANCER"
        );

        mockMvc.perform(
                get("/api/projects")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
        ).andExpect(status().isOk());
    }

    @Test
    void protectedEndpointShouldRejectInvalidJwt()
            throws Exception {

        mockMvc.perform(
                get("/api/projects")
                        .header(
                                "Authorization",
                                "Bearer invalid.jwt.token"
                        )
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void usersEndpointShouldRejectUnauthenticatedRequest()
            throws Exception {

        mockMvc.perform(
                get("/api/users")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void usersEndpointShouldAllowClientRole()
            throws Exception {

        String token = jwtService.generateToken(
                "client@skilllance.com",
                "CLIENT"
        );

        mockMvc.perform(
                get("/api/users")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
        ).andExpect(status().isOk());
    }

    @Test
    void usersEndpointShouldAllowFreelancerRole()
            throws Exception {

        String token = jwtService.generateToken(
                "freelancer@skilllance.com",
                "FREELANCER"
        );

        mockMvc.perform(
                get("/api/users")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
        ).andExpect(status().isOk());
    }
}