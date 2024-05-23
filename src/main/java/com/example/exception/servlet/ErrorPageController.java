package com.example.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    // exception이 터져서 was까지 가면 request.setAttribute로 아래 오류 내용들을 담아줌.
    // 스프링 부트 3.X부터는 javax -> jakarta
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception"; // 예외
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type"; // 예외 타입
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message"; // 오류 메시지
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri"; // 클라이언트 요청 uri
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name"; // 오류가 발생한 서블릿 이름
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code"; // HTTP 상태 코드

    @RequestMapping("/error-page/404")
    public String error404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String error500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION : {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE : {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE : {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI : {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME : {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE : {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType = {}", request.getDispatcherType()); // 클라이언트가 오류를 요청한건지, 서버 내부에서 오류를 요청한건지 확인하려고(필터가 중복으로 호출돼서)
    }
}
