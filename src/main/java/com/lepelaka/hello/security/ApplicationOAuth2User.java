package com.lepelaka.hello.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

public class ApplicationOAuth2User implements OAuth2User{
  private String id;
  @Getter
  private Collection<? extends GrantedAuthority> authorities;
  @Getter
  private Map<String, Object> attributes;

  public ApplicationOAuth2User(String id, Map<String, Object> attributes) {
    this.id = id;
    this.attributes = attributes;
    this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }
  @Override
  public String getName() {
    return id;
  }
}
