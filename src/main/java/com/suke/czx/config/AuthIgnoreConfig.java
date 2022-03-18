package com.suke.czx.config;

import cn.hutool.core.util.ReUtil;
import com.suke.czx.common.annotation.AuthIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author czx
 */
@Slf4j
@Configuration
public class AuthIgnoreConfig implements InitializingBean {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String ASTERISK = "*";
    @Autowired
    private WebApplicationContext applicationContext;
    @Getter
    @Setter
    private List<String> ignoreUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(mappingInfo -> {
            HandlerMethod handlerMethod = map.get(mappingInfo);
            AuthIgnore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AuthIgnore.class);
            if (method != null) {
                Optional.ofNullable(mappingInfo.getPatternsCondition())
                        .map(PatternsRequestCondition::getPatterns)
                        .orElseGet(Collections::emptySet)
                        .forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK)));
            }
        });
    }
}
