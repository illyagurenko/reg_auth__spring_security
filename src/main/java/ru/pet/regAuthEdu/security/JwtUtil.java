package ru.pet.regAuthEdu.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component// сделаем из этого класса бин/инструмент, чтобы использовать его в других местах
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Long lifetime;

    // Метод генерации токена
    public String generatedToken(String username){
        Date issueDate = new Date(); // Время выдачи
        Date expiredDate = new Date(issueDate.getTime() + lifetime);// время жизни токена

        return Jwts.builder()
                .subject(username) // вкладываем в токе имя пользователя
                .issuedAt(issueDate) // когда выдан
                .expiration(expiredDate) // до какого времени годен
                .signWith(getSigningKey()) // Ставим криптографическую подпись
                .compact(); // Собираем всё в одну строчку
    }

    //Метод для извлечения имени из токена
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Проверяем подпись нашим секретным ключом
                .build()
                .parseSignedClaims(token) // Расшифровываем токен
                .getPayload()
                .getSubject(); // Достаем имя пользователя
    }

    //  Вспомогательный метод для получения ключа шифрования
    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
