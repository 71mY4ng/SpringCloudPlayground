package com.timyang.playground.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timyang.playground.api.BasicSpringBootTest;
import com.timyang.playground.api.controller.dto.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AuthApiControllerTest extends BasicSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    static final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @Test
    void login() throws Exception {
        var loginReq = new AuthRequest("fakeusername", "P@ssword");

        mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(loginReq))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(loginReq.getUsername()))
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.authToken").isNotEmpty())
        ;
    }

    @Test
    void refreshToken() {
    }

    @Test
    void list() {
    }
}