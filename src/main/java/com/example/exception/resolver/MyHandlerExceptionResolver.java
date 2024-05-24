package com.example.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("call resolver ", ex); // exception은 {} 안해줘도 됨

            try {
                if (ex instanceof IllegalArgumentException) {
                    log.info("IllegalArgumentException resolver to 400");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // 400 error로 바꿔치기
                    return new ModelAndView(); // 새로운 빈값의 ModelAndView로 반환(was까지)
                    // return 빈 ModelAndView ->  뷰를 렌더링 하지 않고 정상 흐름으로 서블릿이 리턴
                    // return ModelAndView 지정 -> `ModelAndView` 에 `View` , `Model` 등의 정보를 지정해서 반환하면 뷰를 렌더링 한다.
                }

                /*if (ex ...) {
                    response.getWriter().println("fdsfdsfsafs or {like json}"); -> 직접 데이터 넣기
                }*/

            } catch (IOException e) {
                log.error("resolver ex", e);
            }

        return null; // null 을 반환하면, 다음 `ExceptionResolver` 를 찾아서 실행한다.(여러 개가 등록 가능해서)
                     // 만약 처리할 수 있는 `ExceptionResolver` 가 없으면 예외 처리가 안되고, 기존에 발생한 예외를 서블릿 밖으로 던진다. -> 이 경우 was까지 exception이 날라가 500으로 처리됨
    }

}
