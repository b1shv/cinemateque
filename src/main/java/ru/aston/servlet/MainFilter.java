package ru.aston.servlet;

import javax.servlet.*;
import java.io.IOException;

public class MainFilter implements Filter {
    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(CHARACTER_ENCODING);
        servletResponse.setCharacterEncoding(CHARACTER_ENCODING);
        servletResponse.setContentType(CONTENT_TYPE);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
