package com.lepelaka.hello.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lepelaka.hello.model.UserEntity;
import com.lepelaka.hello.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OauthUserServiceImpl extends DefaultOAuth2UserService{
  @Autowired
  private UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // TODO Auto-generated method stub
    OAuth2User oAuth2User = super.loadUser(userRequest);
    try {
      log.info("Oauth2 User attr :: {}" + new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    final String username = (String)oAuth2User.getAttribute("login");
    final String authProvider = userRequest.getClientRegistration().getClientName();

    log.info(username);
    log.info(authProvider);

    UserEntity userEntity = null;

    if(!userRepository.existsByUsername(username)) {
      userEntity = UserEntity.builder().username(username).authProvider(authProvider).build();
      userEntity = userRepository.save(userEntity);
    }
    else {
      userEntity = userRepository.findByUsername(username);
    }

    log.info("succucssfully pulled user info username {} authProvider {}", username, authProvider);

    return new ApplicationOAuth2User(userEntity.getId(), oAuth2User.getAttributes());
  }
}
