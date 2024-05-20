package com.example.helloworld;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloEndpointShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/besarta"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }
}
