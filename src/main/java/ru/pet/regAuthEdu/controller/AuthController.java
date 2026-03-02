package ru.pet.regAuthEdu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.regAuthEdu.dto.JwtResponse;
import ru.pet.regAuthEdu.dto.LoginRequest;
import ru.pet.regAuthEdu.dto.RegisterRequest;
import ru.pet.regAuthEdu.dto.UserDto;
import ru.pet.regAuthEdu.security.JwtUtil;
import ru.pet.regAuthEdu.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    // проверка паролей в Spring Security
    private final AuthenticationManager authenticationManager;

    // Генератор токенов
    private final JwtUtil jwtUtil;


    // пользователь отправляет данные с помощью метода из сервиса записываем в бд и возврвщвем без пароля
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest){
        UserDto newUser = userService.registerUser(registerRequest);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            // Пытаемся авторизовать пользователя (Сверяем логин и пароль с БД)
            // Если пароль не совпадет, Spring сам выбросит ошибку BadCredentialsException
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
            // Далее код работает если логин и пароль верные
            String token = jwtUtil.generatedToken(loginRequest.getUsername());

            return ResponseEntity.ok(new JwtResponse(token));
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }
}
