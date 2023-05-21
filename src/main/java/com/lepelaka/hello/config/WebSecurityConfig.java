package com.lepelaka.hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter;

import com.lepelaka.hello.security.JwtAuthenticationFilter;
import com.lepelaka.hello.security.OAuthSuccessHandler;
import com.lepelaka.hello.security.OauthUserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
@SuppressWarnings("deprecation")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private OauthUserServiceImpl oauthUserServiceImpl;

  @Autowired
  private OAuthSuccessHandler oauthSuccessHandler;
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http 시큐리티 빌더
    http.cors() // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
        .and()
        .csrf()// csrf는 현재 사용하지 않으므로 disable
        .disable()
        .httpBasic()// token을 사용하므로 basic 인증 disable
        .disable()
        .sessionManagement()  // session 기반이 아님을 선언
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests() // /와 /auth/** 경로는 인증 안해도 됨.
        .antMatchers("/", "/auth/**", "/oauth2/**", "/test/**", "/myjsp").permitAll()
        .anyRequest() // /와 /auth/**이외의 모든 경로는 인증 해야됨.
        .authenticated()
        .and()
        .oauth2Login()
        .redirectionEndpoint()
        .baseUri("/oauth2/callback/*")
        .and()
        .authorizationEndpoint()
        .baseUri("/auth/authorize")
        .and()
        .userInfoEndpoint()
        .userService(oauthUserServiceImpl)
        .and()
        .successHandler(oauthSuccessHandler)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
        
        // 40288099883c3baf01883c45dd910000
        // 40288099883c3baf01883c45dd910000
        ;

    // filter 등록.
    // 매 요청마다
    // CorsFilter 실행한 후에
    // jwtAuthenticationFilter 실행한다.

    log.info("{}",jwtAuthenticationFilter);
    http.addFilterAfter(
        jwtAuthenticationFilter,
        CorsFilter.class
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}