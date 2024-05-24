package com.example.exception.api;

import com.example.exception.userexception.BadRequestException;
import com.example.exception.userexception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello api exception :  " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    // ResponseExceptionResolver -> HTTP 응답 코드 변경
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException()); // exception 직접 넣기
    }

    // DefaultHandlerExceptionResolver -> 스프링 내부 예외 처리
    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok"; // 원래라면 자료형을 다르게 입력했을 때 TypeMissMatch가 떠서 was에 그대로 넘어가서 500에러가 떠야 하는데 스프링에서 자동으로 클라이언트 실수는 400으로 바꿔줌
    }


    @Data
    @AllArgsConstructor
    static class  MemberDto {
        private String memberId;
        private String name;
    }


}
