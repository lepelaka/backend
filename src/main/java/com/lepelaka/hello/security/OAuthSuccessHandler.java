package com.lepelaka.hello.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    TokenProvider provider = new TokenProvider();
    String token = provider.create(authentication);    
    response.getWriter().write(token);
    log.info("token :: {}", token);
    response.sendRedirect("http://localhost:3000/socialLogin?token="+token);
  }
}