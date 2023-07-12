package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    // TodoDTO 뿐만 아니라 다른 모델의 DTO 도 이 클래스를 이용해 리턴하도록 Generic 사용
    private String error;
    private List<T> data;
}
