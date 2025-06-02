package cmahy.simple.spring.webapp.resource.impl.adapter.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class SimpleWebPageConfigurer implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/without-controller").setViewName("no-controller");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/access-denied").setViewName("access-denied");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String uri = request.getRequestURI();

                if (uri.startsWith("/should-be-redirect")) {
                    String newUri = "/juste-un-prefix-de-plus" + uri + (StringUtils.isNotBlank(request.getQueryString()) ? ("?" + request.getQueryString()) : "");

                    response.sendRedirect(newUri);

                    return false;
                }

                return true;
            }
        }).addPathPatterns("/should-be-redirect/**");
    }
}
