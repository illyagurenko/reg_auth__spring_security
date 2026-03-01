package ru.pet.regAuthEdu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pet.regAuthEdu.dto.RegisterRequest;
import ru.pet.regAuthEdu.dto.UserDto;
import ru.pet.regAuthEdu.entity.Role;
import ru.pet.regAuthEdu.entity.User;
import ru.pet.regAuthEdu.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        User saveUser = userRepository.save(user);
        return new UserDto(saveUser.getId(), saveUser.getUsername(), saveUser.getEmail());
    }
}
