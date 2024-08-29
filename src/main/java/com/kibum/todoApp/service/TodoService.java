package com.kibum.todoApp.service;

import com.kibum.todoApp.dto.TodoRequestDto;
import com.kibum.todoApp.dto.TodoResponseDto;
import com.kibum.todoApp.dto.TodoUpdateDto;
import com.kibum.todoApp.entity.Todo;
import com.kibum.todoApp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponseDto> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(TodoResponseDto::of)
                .toList();
    }

    public TodoResponseDto getTodoById(Long id) {
        Todo entity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 todo 입니다."));

        return TodoResponseDto.of(entity);
    }

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        Todo todo = Todo.builder()
                .title(todoRequestDto.getTitle())
                .completed(false)
                .build();
        Todo savedTodo = todoRepository.save(todo);
        return new TodoResponseDto(savedTodo.getId(), savedTodo.getTitle(), savedTodo.isCompleted());
    }

    @Transactional
    public Optional<TodoResponseDto> updateTodo(Long id, TodoUpdateDto todoUpdateDto) {
        return todoRepository.findById(id).map(todo -> {
            if (todoUpdateDto.getTitle() != null) {
                todo.updateTitle(todoUpdateDto.getTitle());
            }
            if (todoUpdateDto.getCompleted() != null) {
                todo.updateCompleted(todoUpdateDto.getCompleted());
            }
            return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.isCompleted());
        });
    }

    public void deleteTodoById(Long id) {
        Todo entity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 todo 입니다."));

        todoRepository.delete(entity);
    }
}
