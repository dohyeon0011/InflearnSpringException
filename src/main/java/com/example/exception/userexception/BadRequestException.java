package com.example.exception.userexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") // reason = "" 직접 입력 가능
public class BadRequestException extends RuntimeException {

}
