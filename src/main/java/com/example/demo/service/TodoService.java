package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.pesristense.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        // TodoEntity 저장
        repository.save(entity);

        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        // validations
        validate(entity);

        // db에 저장 / 로그 남기기
        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        // 저장된 엔티티를 포함한 새로운 리스트 반환
        return repository.findByUserId(entity.getUserId());
    }

    // update method
    public List<TodoEntity> update(final TodoEntity entity) {
        // 1. validation
        validate(entity);

        // 2. 넘겨 받은 엔티티 id로 TodoEntity 가져옴 존재하지 않는 엔티티는 업데이트 할 수 없기 때문.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            // 3. 반환 받은 TodoEntity 가 존재하면 값을 새 entity 의 값으로 덮어 씀
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

//             3-1 lambda가 익숙하지 않다면...
//            if (original.isPresent()) {
//                // 3. 반환 받은 TodoEntity 가 존재하면 값을 새 entity 의 값으로 덮어 씀
//                final TodoEntity todo = original.get();
//                todo.setTitle(entity.getTitle());
//                todo.setDone(entity.isDone());
//            }

            // 4. db에 새 값 저장
            repository.save(todo);
        });

        //  Retrieve Todo에 만들어 놓은 메서드로 유저의 모든 TODOList return
        return getTodos(entity.getUserId());
    }

    // repository 의 findByUserId 를 이용한 코드
    public List<TodoEntity> getTodos(final String userId) {
        return repository.findByUserId(userId);
    }


    public List<TodoEntity> delete(final TodoEntity entity) {
        // 1. validation
        validate(entity);

        try {
            // delete entity
            repository.delete(entity);
        } catch (Exception e) {
            // exception => logging
            log.error("error deleting entity ", entity.getId(), e);

            // 컨트롤러로 exception 보냄
            // db 내부 로직을 캡슐화 하기 위해 e를 리턴하는게 아니고 새로운 Exception 오브젝트 리턴
            throw new RuntimeException("error deleting entity ");
        }
        // return new TodoList
        return getTodos(entity.getUserId());
    }

    // 넘어온 엔티티가 유효한지 검사하는 로직
    // 나중에 코드가 길어지면 따로 java 파일로 만들 수 있음
    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
