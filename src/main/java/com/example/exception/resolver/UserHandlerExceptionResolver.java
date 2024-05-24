package com.example.exception.resolver;

import com.example.exception.userexception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {

            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept");  // Content Type 가져오기
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 에러로 바꿔치기

                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult); // json -> string

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);

                    return new ModelAndView(); // 여기서 끝내버리고 처음에 터진 예외는 먹어버리지만 서블릿까지 정상적으로 Response에 담겨 전달
                } else {
                    // TEXT/HTML
                    return new ModelAndView("error/500"); // 렌더링 할 뷰를 직접 지정
                }
            }

        } catch (Exception e) {
            log.info("resolver ex", e);
        }

        return null;
    }
}
