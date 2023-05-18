package com.lepelaka.hello.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lepelaka.hello.model.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {
  List<TodoEntity> findByUserId(String userId);
}
