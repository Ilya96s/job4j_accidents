package ru.job4j.accidents.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginController - контроллер, отвечающий за обработку страницы входа в приложение и выхода из него
 *
 * @author Ilya Kaltygin
 */
@Controller
public class LoginController {

    /**
     * Возвращает пользователю страницу с фофрмой входа в приложение
     *
     * @param error  параметр error (ошибка при входе в приложение)
     * @param logout параметр logout (успешный выход из системы)
     * @param model  модель с данными
     * @return страница с формой для входа
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Логин или пароль введены не верно !";
        }
        if (logout != null) {
            errorMessage = "Вы успешно вышли из системы !";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "users/login";
    }

    /**
     * Отвечает за завершение сеанса текущей аутентификации пользователя
     *
     * @param request  запрос
     * @param response ответ
     * @return перенаправляет пользователя по url "/login" с параметром "?logout=true" для того, что бы было отображено сообщение
     * что пользователь успешно вышел из системы
     */
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        if (aut != null) {
            new SecurityContextLogoutHandler().logout(request, response, aut);
        }
        return "redirect:/login?logout=true";
    }
}
