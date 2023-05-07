package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Тесты на контроллер AccidentController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Тест на метод get("/createAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentPage() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"));
    }

    /**
     * Тест на метод get("/formUpdateAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldReturnUpdateAccidentPageForAccidentId2() throws Exception {
        this.mockMvc.perform(get("/formUpdateAccident?id=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/update"));
    }

    /**
     * Тест на метод get("/formUpdateAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldReturnErrorPageWhenAccidentNotFound() throws Exception {
        this.mockMvc.perform(get("/formUpdateAccident?id=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/error"));
    }

}