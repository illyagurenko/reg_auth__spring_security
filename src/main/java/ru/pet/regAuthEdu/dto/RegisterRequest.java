package ru.pet.regAuthEdu.dto;

import lombok.Data;
// запрос на регистрацию, отправляет пользователь
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}
