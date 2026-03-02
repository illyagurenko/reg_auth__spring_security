package ru.pet.regAuthEdu.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {// Фильтр, который срабатывает 1 раз на каждый запрос

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Достаем заголовок Authorization из запроса
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        // Проверяем, есть ли заголовок и начинается ли он с Bearer (стандарт для токенов)
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7);// Отрезаем слово "Bearer " (7 символов), чтобы получить сам токен
            try{
                username = jwtUtil.extractUsername(jwt);
            }
            catch (Exception e){
                System.out.println("Ошибка" + e.getMessage());
            }
        }
        //Если имя достали, а в текущем контексте Security еще нет авторизации (юзер еще не залогинен в этом запросе)
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Достаем пользователя из БД
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Создаем объект аутентификации (пропуск)
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // пароль нам тут уже не нужен, токен всё доказал
                    userDetails.getAuthorities()// кладем роли (USER, ADMIN)
            );

            //Кладем этот пропуск в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Обязательно передаем запрос дальше по цепочке фильтров
        filterChain.doFilter(request, response);
    }

}
