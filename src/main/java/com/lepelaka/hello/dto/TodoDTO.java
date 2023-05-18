package com.lepelaka.hello.dto;

import com.lepelaka.hello.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
  private String id;
  private String title;
  private boolean done;

  // entity > dto
  public TodoDTO(final TodoEntity entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.done = entity.isDone();
  }

  // dto > entity
  public static TodoEntity toEntity(final TodoDTO dto) {
    return TodoEntity.builder().id(dto.id).title(dto.title).done(dto.done).build();
  }
}
