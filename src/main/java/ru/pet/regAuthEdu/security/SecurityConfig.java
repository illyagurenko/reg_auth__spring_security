package ru.pet.regAuthEdu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем защиту CSRF, так как для REST API она не нужна
                // 2. Настраиваем правила доступа
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем всем доступ к /auth/register и /auth/login
                        .requestMatchers("/auth/**").permitAll()
                        // Все остальные запросы (например /admin) требуют авторизации
                        .anyRequest().authenticated());
        return http.build();
    }
}
