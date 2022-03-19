package com.suke.czx.config;

import cn.hutool.core.util.ReUtil;
import com.suke.czx.common.annotation.AuthIgnore;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 获取有 @AuthIgnore 注解的接口路径
 */
@Slf4j
@Configuration
public class AuthIgnoreConfig {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)}");
    private static final String ASTERISK = "*";
    @Getter
    private final List<String> ignoreUrls;

    public AuthIgnoreConfig(WebApplicationContext applicationContext) {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        ignoreUrls = map.entrySet()
                .stream()
                .flatMap(e -> {
                    RequestMappingInfo mappingInfo = e.getKey();
                    HandlerMethod method = e.getValue();
                    AuthIgnore annotation = AnnotationUtils.findAnnotation(method.getMethod(), AuthIgnore.class);
                    return Optional.ofNullable(annotation)
                            .map(v -> mappingInfo.getPathPatternsCondition())
                            .map(PathPatternsRequestCondition::getPatterns)
                            .stream()
                            .flatMap(v -> v.stream()
                                    .map(vv -> ReUtil.replaceAll(vv.getPatternString(), PATTERN, ASTERISK)));
                })
                .toList();
    }
}
