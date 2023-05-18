package com.lepelaka.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lepelaka.hello.model.UserEntity;
import com.lepelaka.hello.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity) {
    if (userEntity == null || userEntity.getUsername() == null) {
      throw new RuntimeException("Invalid arguments");
    }

    final String username = userEntity.getUsername();
    log.info("{}", username);
    log.info("{}", userRepository);
    log.info("{}", userRepository.existsByUsername(username));
    if (userRepository.existsByUsername(username)) {
      log.info("username already exist {}", username);
      throw new RuntimeException("username already exist");
    }
    userRepository.save(userEntity);
    UserEntity entity = userRepository.save(userEntity);
    log.info("{}", entity);
    return entity;
  }

  public UserEntity getByCredential(final String username, final String password) {
    return userRepository.findByUsernameAndPassword(username, password);
  }
}
