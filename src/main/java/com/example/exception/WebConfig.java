package com.example.exception;

import com.example.exception.filter.LogFilter;
import com.example.exception.interceptor.LogInterceptor;
import com.example.exception.resolver.MyHandlerExceptionResolver;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 인터셉터는 디스패처의 타입을 정할 수 없음(그래서 path로 빼버림)
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "*.ico", "/error-page/**"); // 오류 페이지 경로 모두 다 제외
    }

    // HandlerExceptionResolver 등록하기
    // extendHandlerExceptionResolvers 메서드로 해야 기존 스프링의 기본 세팅이 사리지지 않음.
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
    }

    //    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST); // default가 request, 클라이언트의 요청이 있는 경우에만 필터 호출(서버 내부 오류 요청은 호출x)

        return filterRegistrationBean;
    }
}
