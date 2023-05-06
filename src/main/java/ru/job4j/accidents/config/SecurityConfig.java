package ru.job4j.accidents.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * SecurityConfig - конфигурационный класс для Spring Security
 *
 * @author Ilya Kaltygin
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Создает бин BCryptPasswordEncoder - реализация PasswordEncoder. Использует алгоритм BCrypt для хэширования паролей
     *
     * @return объект типа PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    DataSource ds;

    /**
     * Используется для настройки аутентификации пользователей в приложении
     * @param auth предоставляет возможность настраивать способы аутентификации пользователей
     * @throws Exception exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery("select username, password, enabled "
                        + "from users "
                        + "where username = ?")
                .authoritiesByUsernameQuery(
                        " select u.username, a.authority "
                                + "from authorities as a, users as u "
                                + "where u.username = ? and u.authority_id = a.id");
    }

    /**
     * Используется для настройки авторизации и конфигурирования страниц входа в приложение
     * @param http используется для настройки различных аспектов безопасности приложения
     * @throws Exception exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/reg")
                .permitAll()
                .antMatchers("/**")
                .hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

}
