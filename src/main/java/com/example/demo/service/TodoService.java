package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.pesristense.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TodoEntity> create(final TodoEntity entity){
        // validations
        validate(entity);

        // db에 저장 / 로그 남기기
        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        // 저장된 엔티티를 포함한 새로운 리스트 반환
        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        // 1. validation
        validate(entity);

        // 2. 넘겨 받은 엔티티 id로 TodoEntity 가져옴 존재하지 않는 엔티티는 업데이트 할 수 없기 때문.

    }
    // repository 의 findByUserId 를 이용한 코드
    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    // 넘어온 엔티티가 유효한지 검사하는 로직
    // 나중에 코드가 길어지면 따로 java 파일로 만들 수 있음
    private void validate(final TodoEntity entity){
        if (entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
