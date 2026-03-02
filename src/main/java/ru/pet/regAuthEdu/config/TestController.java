package ru.pet.regAuthEdu.config;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Principal - это встроенный объект Спринга, в котором лежит имя текущего залогиненного юзера
import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(Principal principal){
        return ResponseEntity.ok("привет, " + principal.getName() + " авторизация пройдена");
    }
}
