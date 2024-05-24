package com.example.exception.api;

import com.example.exception.userexception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionV3Controller {

    /*// 서블릿 컨테이너까지 안 가서 여기서 모든게 그냥 다 끝나버림(근데 얜 정상 처리와 예외 처리가 같이 있음)
    // IllegalArgumentException 예외가 터지면 이 메서드가 실행
    // IllegalArgumentException의 자식까지도 잡아줌
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 상태 코드 400으로 바꾸기
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.info("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage()); // 바로 정상 흐름으로 바꿔서(+객체까지) 리턴(상태 코드가 200이 되버림)
    }

    // UserException의 자식까지도 잡아줌
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) { // 어노테이션 안에 ("UserException.class") 넣고 파라미터 생략 가능
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // Exception으로 두면 위에서 처리하지 못한 예외들이 최상위인 Exception << 여기로 다 넘어옴.
    // 공통으로 처리할 예외들을 여기서 처리
//    @ExceptionHandler({RuntimeException.class, IllegalAccessError.class}) 이런 식으로 다양한 예외를 한번에 처리 가능
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
*/

    @GetMapping("/api3/members/{id}")
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

    @Data
    @AllArgsConstructor
    static class  MemberDto {
        private String memberId;
        private String name;
    }

}
