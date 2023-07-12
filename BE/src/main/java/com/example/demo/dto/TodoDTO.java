package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 서비스에서 클라이언트로 값을 반환할 때 모델 자체를 그대로 리턴하지 않는다.
 * 보통 DTO data transfer object 로 변환한다.
 * 왜 ??
 * 1. 비즈니스 로직을 캡슐화 하기 위해서.
 * 모델은 데이터베이스 테이블 구조와 유사함.
 * 모델이 가진 필드는 테이블의 스키마와 비슷하다.
 * 회사들은 외부인이 자사의 DB 스키마 구조를 알지 못하게
 * DTO 객체로 바꾸어 반환하면 내부 로직과 db 구조를 숨길 수 있다.
 *
 * 2. 클라이언트가 필요로 하는 정보를 모델이 전부 포함하지 못하는 경우가 있기 때문
 *    모델은 서비스 로직과는 관련이 없기 때문에 모델에 담기가 애매하다.
 *    이런 경우 dto 에 에러 메세지 필드를 선언하고 dto에 포함하면 된다.
 * */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    // http 응답을 반환 할 때 추가적인 정보를 함께 반환해야 하므로 dto 사용.
    // 컨트롤러는 유저에게서 todoDTO 객체를 요청받아 TodoEntity 로 변환해서 저장해야 한다.
    // TodoService create() 메서드가 반환하는 TodoEntity 를 TodoDTO 로 변환해 리턴해야 한다.
    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone()).build();
    }
}

