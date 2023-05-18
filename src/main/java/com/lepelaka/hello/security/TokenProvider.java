package com.lepelaka.hello.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.lepelaka.hello.model.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenProvider {
  private static final String SECRET_KEY = "1234";

  public String create(UserEntity userEntity) {
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        // .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
        .setSubject(userEntity.getId()).setIssuer("hello app").setIssuedAt(new Date()).setExpiration(expiryDate)
        .compact();
  }

  public String validateAndGetUserId(String token) {
    return Jwts
        // .parserBuilder()
        // .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
        // // .parser()
        // .build()
        // .parseClaimsJws(token).getBody().getSubject();

        .parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token).getBody().getSubject();
  }

  public static void main(String[] args) {
    TokenProvider provider = new TokenProvider();
    String[] arr = {
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MDI4ODA5Yjg4MmRhNmRhMDE4ODJkYTZlYjZiMDAwMCIsImlzcyI6ImhlbGxvIGFwcCIsImlhdCI6MTY4NDM5NjY0OSwiZXhwIjoxNjg0NDgzMDQ5fQ.kpMKJiQ4x5_ebYBCMqD2dzd8h-nBIZWiuxs7LYwj5XaaWd1CHn6YegJSuLIjY6SM8hzdLA8bVmgJeDEXEVOdSQ",
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MDI4ODA5Yjg4MmRhNmRhMDE4ODJkYTZlYjZiMDAwMCIsImlzcyI6ImhlbGxvIGFwcCIsImlhdCI6MTY4NDM5NjY2NSwiZXhwIjoxNjg0NDgzMDY1fQ.6Y-_ZmwBcbx-jgkRU0Wa3fZK-w9jHwsaXvSeiS5nPy383Gb3obggkD60SzJs-D6y9Eg7PK-vcq_JAgsgmOp5aA",
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MDI4ODA5Yjg4MmRhNmRhMDE4ODJkYTZlYjZiMDAwMCIsImlzcyI6ImhlbGxvIGFwcCIsImlhdCI6MTY4NDM5NjY3OCwiZXhwIjoxNjg0NDgzMDc4fQ.4F32_KcsKynJ8IbcDf8XG03yVgECMH-8ZrynWWcuwOZtYhEDLzZ9fygyT-7iJ6udqASlk-6Bo9OApNaOHgY1dw"
    };

    Arrays.asList(arr).forEach(t -> System.out.println(provider.validateAndGetUserId(t)));

    // Decoder decoder = Base64.getDecoder();
    // byte[] result = decoder.decode(token);
    // System.out.println(new String(result));
  }
}
