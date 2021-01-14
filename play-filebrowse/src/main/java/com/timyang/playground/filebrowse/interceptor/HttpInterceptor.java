package com.timyang.playground.filebrowse.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String path = request.getServletPath();
        final String query = request.getQueryString();

        final String queryVar =
                StringUtils.isNotBlank(query) ? URLEncoder.encode(query, StandardCharsets.UTF_8.name()) : "no_query";

        log.info("path:{}|query:{}|var:{}|method:{}", path, query, queryVar, request.getMethod());

        return super.preHandle(request, response, handler);
    }
}
