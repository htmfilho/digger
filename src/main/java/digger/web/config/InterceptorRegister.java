package digger.web.config;

import digger.web.interceptor.LoggerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorRegister implements WebMvcConfigurer {

    private LoggerInterceptor loggerInterceptor;

    public InterceptorRegister(LoggerInterceptor loggerInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor).addPathPatterns("/admin/**", "/datasources/**", "/users/**", "/error");
    }
}