package ru.pet.regAuthEdu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    // благодаря Configuration спринг заходит в этот класс а Bean выполняет этот метод и вернет в сервис
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
