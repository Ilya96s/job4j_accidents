package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.springdata.SpringDataAccidentService;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private SpringDataAccidentService springDataAccidentService;

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
        var accidentType = new AccidentType(1, "AccidentType");

        var accidentRule = Set.of(new Rule(1, "accidentRule"));

        var accident = Accident.builder()
                .id(0)
                .name("name")
                .text("text")
                .address("address").type(accidentType)
                .rules(accidentRule)
                .build();

        Mockito.when(springDataAccidentService.findById(2)).thenReturn(Optional.of(accident));

        this.mockMvc.perform(get("/formUpdateAccident?id=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));
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

    /**
     * Тест на успешное выполнение метода post ("/saveAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldRedirectToMainPageAfterSaveAccident() throws Exception {
        var accidentType = new AccidentType(1, "AccidentType");

        var accidentRule = Set.of(new Rule(1, "accidentRule"));

        var accident = Accident.builder()
                .id(0)
                .name("name")
                .text("text")
                .address("address").type(accidentType)
                .rules(accidentRule)
                .build();

        Mockito.when(springDataAccidentService.save(any(), any())).thenReturn(Optional.of(accident));

        mockMvc.perform(post("/saveAccident")
                        .param("id", "0")
                        .param("name", "name")
                        .param("text", "text")
                        .param("address", "address")
                        .param("type.id", "1")
                        .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(springDataAccidentService).save(argument.capture(), any());

        assertThat(argument.getValue().getId(), is(0));
        assertThat(argument.getValue().getName(), is("name"));
        assertThat(argument.getValue().getText(), is("text"));
        assertThat(argument.getValue().getAddress(), is("address"));
        assertThat(argument.getValue().getType().getId(), is(1));
    }

    /**
     * Тест на неудачное выполнение метода post ("/saveAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldReturnErrorPageAfterFailedSaveAccident() throws Exception {
        Mockito.when(springDataAccidentService.save(any(), any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/saveAccident")
                        .param("id", "0")
                        .param("name", "name")
                        .param("text", "text")
                        .param("address", "address")
                        .param("type.id", "1")
                        .param("rIds", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/error"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Не удалось сохранить инцидент"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(springDataAccidentService).save(argument.capture(), any());

        assertThat(argument.getValue().getId(), is(0));
        assertThat(argument.getValue().getName(), is("name"));
        assertThat(argument.getValue().getText(), is("text"));
        assertThat(argument.getValue().getAddress(), is("address"));
        assertThat(argument.getValue().getType().getId(), is(1));
    }

    /**
     * Тест на успешное выполнение метода post ("/updateAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldRedirectToMainPageAfterSuccessUpdateAccident() throws Exception {
        var accidentType = new AccidentType(1, "AccidentType");

        var accidentRule = Set.of(new Rule(1, "accidentRule"));

        var accident = Accident.builder()
                .id(0)
                .name("name")
                .text("text")
                .address("address").type(accidentType)
                .rules(accidentRule)
                .build();

        Mockito.when(springDataAccidentService.update(accident.getId())).thenReturn(true);

        mockMvc.perform(post("/updateAccident")
                        .flashAttr("accident", accident))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(springDataAccidentService).update(accident.getId());
    }

    /**
     * Тест на неудачное выполнение метода post ("/updateAccident")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldReturnErrorPageAfterFailedUpdateAccident() throws Exception {
        Mockito.when(springDataAccidentService.update(1)).thenReturn(false);

        mockMvc.perform(post("/updateAccident")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("errors/error"));

        verify(springDataAccidentService).update(1);
    }

}