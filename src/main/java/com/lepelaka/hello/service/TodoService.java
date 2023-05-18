package com.lepelaka.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lepelaka.hello.model.TodoEntity;
import com.lepelaka.hello.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService {
  @Autowired
  private TodoRepository repository;

  public String testService() {
    // final int[] arr = {5, 10, 15};
    // arr[0] = 50;
    // arr = new int[5];

    TodoEntity entity = TodoEntity.builder().title("My first Todo Item").build();
    repository.save(entity);
    TodoEntity savedEntity = repository.findById(entity.getId()).get();
    return savedEntity.getTitle();
  }

  public List<TodoEntity> create(final TodoEntity entity) {
    validate(entity);

    repository.save(entity);

    log.info("entity id : {} is saved ", entity.getId());

    return repository.findByUserId(entity.getUserId());
  }

  public List<TodoEntity> retrieve(String userId) {
    return repository.findByUserId(userId);
  }

  public List<TodoEntity> update(final TodoEntity entity) {
    validate(entity);

    // TodoEntity origin = repository.findById(entity.getId()).get();
    final Optional<TodoEntity> origin = repository.findById(entity.getId());

    origin.ifPresent(todo -> {

      todo.setTitle(entity.getTitle());
      todo.setDone(entity.isDone());
      repository.save(todo);
    });

    return repository.findByUserId(entity.getUserId());
  }

  public List<TodoEntity> delete(final TodoEntity entity) {
    validate(entity);
    try {
      repository.delete(entity);
    } catch (Exception e) {
      log.error("error deleting entity", entity.getId(), e);
      throw new RuntimeException("error deleting entity" + entity.getId());
    }
    return repository.findByUserId(entity.getUserId());
  }

  private void validate(final TodoEntity entity) {
    if (entity == null) {
      log.warn("Entity cannot be null");
      throw new RuntimeException("Entity cannot be null");
    }

    if (entity.getUserId() == null) {
      log.warn("Unknown user.");
      throw new RuntimeException("Unknown user.");
    }
  }
}
