package com.kibum.todoApp.dto;

import com.kibum.todoApp.entity.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoResponseDto {

    private Long id;
    private String title;
    private boolean completed;

    public static TodoResponseDto of(Todo todo) {
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.isCompleted());
    }
}
