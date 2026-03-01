package ru.pet.regAuthEdu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.regAuthEdu.dto.RegisterRequest;
import ru.pet.regAuthEdu.dto.UserDto;
import ru.pet.regAuthEdu.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    // пользователь отправляет данные с помощью метода из сервиса записываем в бд и возврвщвем без пароля
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest){
        UserDto newUser = userService.registerUser(registerRequest);
        return ResponseEntity.ok(newUser);
    }
}
