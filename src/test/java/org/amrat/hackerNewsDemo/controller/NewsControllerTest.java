package org.amrat.hackerNewsDemo.controller;

import lombok.SneakyThrows;
import org.amrat.hackerNewsDemo.service.NewsServiceImpl;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.security.InvalidParameterException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsServiceImpl newsService;

    @Test
    void whenValidInput_thenGetBestStoriesReturns200() throws Exception {
        mockMvc.perform(get("/best-stories")
                .contentType("application/json"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.email", equalTo("boy@test.com")));
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(10)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenValidInput_thenGetPastStoriesReturns200() throws Exception {
        mockMvc.perform(get("/past-stories")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenGetCommentsReturns200() throws Exception {
        mockMvc.perform(get("/comments/24323778")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(10)));
    }

    @SneakyThrows
    @Test
    void whenInValidInput_thenGetCommentsReturns200() throws InvalidParameterException {
        mockMvc.perform(get("/comments/987987987987")
                .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }
}
