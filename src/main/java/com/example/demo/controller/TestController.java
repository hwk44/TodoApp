package com.example.demo.controller;


import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test") // 리소스
public class TestController {
    @GetMapping
    public String testController(){
        return "hello World";
    }

    @GetMapping("/testGetMapping")
    public  String testControllerWithPath() {
        return "Hello World testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello World! ID " + id;
    }

    // /test경로는 이미 존재하므로 /test/testRequestParam으로 지정했다.
    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello World! ID " + id;
    }

    // /test경로는 이미 존재하므로 /test/testResponseBody 지정했다.
    @GetMapping("/testResponseBody") // ResponseDTO 반환하는 메서드
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDTO");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. And you got 400 OR 200 !");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        // http status 400 으로 설정
//        return ResponseEntity.badRequest().body(response);

        // http status 200 으로 설정
        return ResponseEntity.ok().body(response);
    }

    // /test경로는 이미 존재하므로 /test/testRequestBody로 지정했다.
    @GetMapping("/testRequestBody") // RequestBody 로 넘어오는 JSON 을 TestRequestBodyDTO 객체로 변환해서 가져오라는 뜻
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World! ID" + testRequestBodyDTO.getId() + " Message : "
                + testRequestBodyDTO.getMessage();
    }
}
