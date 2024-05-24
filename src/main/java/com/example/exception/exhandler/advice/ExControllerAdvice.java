package com.example.exception.exhandler.advice;

import com.example.exception.exhandler.ErrorResult;
import com.example.exception.userexception.UserException;
import lombok.ConfigurationKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice(RestController.class) -> @RestController가 붙어있는 애들만
// @RestControllerAdvice("org.example.controllers") -> 패키지 안에 있는 애들만
// @RestControllerAdvice(assignableTypes = {ConfigurationKeys.class, ConfigurationKeys.class}) -> 직접 컨트롤러 클래스 지정
// 여러 컨트롤러에서 발생하는 오류들을 여기서 한번에 처리(대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBinder 기능을 부여함) -> 근데 지금은 대상을 지정 안해서 어떤 컨트롤러에서 발생하는 오류들을 여기서 잡아줌(글로벌 적용)
@Slf4j
@RestControllerAdvice(basePackages = "com.example.exception.api") // @ControllerAdvice + @ResponseBody
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.info("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
