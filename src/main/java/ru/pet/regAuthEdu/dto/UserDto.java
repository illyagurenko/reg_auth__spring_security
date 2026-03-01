package ru.pet.regAuthEdu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Ответ сервера без пароля

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;


}
