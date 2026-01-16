package kr.co.ictedu.someta.chart;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final VisitorLogInterceptor visitorLogInterceptor;

    public WebConfig(VisitorLogInterceptor visitorLogInterceptor) {
        this.visitorLogInterceptor = visitorLogInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorLogInterceptor)
                .addPathPatterns("/**")           // 모든 요청
                .excludePathPatterns("/static/**", "/favicon.ico"); // 정적 리소스 제외
    }
}
