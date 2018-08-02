package com.codecool;

import com.codecool.flatbuddy.FlatbuddyApplication;
import com.codecool.flatbuddy.exception.UserNotFoundException;
import com.codecool.flatbuddy.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlatbuddyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserService userService;

    @Test
    public void shouldReturnHttpStatusCodeAfterAdd() throws Exception {
        Map<String, String> newUser = new HashMap<>();
        newUser.put("email", "test1@test.com");
        newUser.put("password", "test");
        newUser.put("confirmationPassword", "test");

        mvc.perform(post("/register")
            .content(om.writeValueAsBytes(newUser))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAddedUserWithJson() throws Exception {
        Map<String, String> newUser = new HashMap<>();
        newUser.put("email", "test2@test.com");
        newUser.put("password", "test");
        newUser.put("confirmationPassword", "test");

        mvc.perform(post("/register")
            .content(om.writeValueAsBytes(newUser))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test2@test.com"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
