package com.lepelaka.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lepelaka.hello.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
  UserEntity findByUsername(String username);

  boolean existsByUsername(String username);

  UserEntity findByUsernameAndPassword(String username, String password);
}
