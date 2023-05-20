package com.lepelaka.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lepelaka.hello.dto.ResponseDTO;
import com.lepelaka.hello.dto.UserDTO;
import com.lepelaka.hello.model.UserEntity;
import com.lepelaka.hello.security.TokenProvider;
import com.lepelaka.hello.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private PasswordEncoder encoder;


  @PostMapping("signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    log.info("{}", userDTO);
    try {
      UserEntity user = UserEntity.builder().username(userDTO.getUsername()).password(
        encoder.encode(userDTO.getPassword())
      ).build();
      UserEntity createdUser = userService.create(user);
      log.info("{}", createdUser);
      UserDTO dto = UserDTO.builder().id(createdUser.getId()).username(createdUser.getUsername()).build();
      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      log.info(e.getMessage());
      return ResponseEntity.badRequest().body(ResponseDTO.builder().error(e.getMessage()).build());
    }
  }

  @PostMapping("signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
    UserEntity user = userService.getByCredential(userDTO.getUsername(), userDTO.getPassword(), encoder);

    if (user != null) {
      UserDTO dto = UserDTO.builder()
          .id(user.getId())
          .username(user.getUsername())
          .token(tokenProvider.create(user))
          .build();

      return ResponseEntity.ok().body(dto);
    } else {
      return ResponseEntity.badRequest().body(ResponseDTO.builder().error("login failed").build());
    }

  }
}
