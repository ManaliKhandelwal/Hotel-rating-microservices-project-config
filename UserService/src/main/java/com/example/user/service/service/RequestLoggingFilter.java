package com.example.user.service.service;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RequestLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        try {
            chain.doFilter(request, response);

        } catch (Exception ex) {

            log.error("Exception occurred while processing request: {} {}",
                    req.getMethod(),
                    req.getRequestURI(),
                    ex);

            throw ex;
        }
    }
}
