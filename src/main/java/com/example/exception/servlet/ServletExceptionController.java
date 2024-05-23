package com.example.exception.servlet;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServletExceptionController {

    // 예외가 터진거라 was까지 전달돼서 was가 기본 에러 페이지를 띄워줌
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    // response로 exception이 터진게 아니라 반환은 된거라 리턴 리턴 돼서
    // was에서 response 까보게 되는데 에러가 담긴걸 보고 was가 500 에러 기본 페이지 띄워줌
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
