package com.example.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값"); // 서버 내부에서 exception을 터트려 was에 전달된 것이므로 IllegalArgumentException이 아니라 500에러가 뜸
        }

        return new MemberDto(id, "hello api exception :  " + id);
    }

    @Data
    @AllArgsConstructor
    static class  MemberDto {
        private String memberId;
        private String name;
    }


}
