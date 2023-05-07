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
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.springdata.SpringDataUserService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты на контроллер RegController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Сервис по работе с пользователями
     */
    @MockBean
    private SpringDataUserService springDataUserService;

    /**
     * Тест на метод get("/reg")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldReturnRegPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/reg"));
    }

    /**
     * Тест на успешное выполнение метода post ("/reg")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldRedirectToLoginPageAfterSuccessSaveUser() throws Exception {
        var authority = Authority.builder()
                .id(1)
                .authority("ROLE_ADMIN")
                .build();

        var user = User.builder()
                .id(1)
                .username("user")
                .password("123")
                .enabled(true)
                .authority(authority)
                .build();

        Mockito.when(springDataUserService.save(user)).thenReturn(Optional.of(user));

        this.mockMvc.perform(post("/reg")
                        .param("username", "user")
                        .param("password", "123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(springDataUserService).save(argument.capture());

        assertThat(argument.getValue().getUsername(), is("user"));
        assertThat(argument.getValue().getPassword(), is("123"));
    }

    /**
     * Тест на неудачное выполнение метода post ("/reg")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void shouldRedirectToRegPageAfterFiledSaveUser() throws Exception {
        var authority = Authority.builder()
                .id(1)
                .authority("ROLE_ADMIN")
                .build();

        var user = User.builder()
                .id(1)
                .username("user")
                .password("123")
                .enabled(true)
                .authority(authority)
                .build();

        Mockito.when(springDataUserService.save(user)).thenReturn(Optional.empty());

        this.mockMvc.perform(post("/reg")
                        .param("username", "user")
                        .param("password", "123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reg?error=true"));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(springDataUserService).save(argument.capture());

        assertThat(argument.getValue().getUsername(), is("user"));
        assertThat(argument.getValue().getPassword(), is("123"));
    }
}