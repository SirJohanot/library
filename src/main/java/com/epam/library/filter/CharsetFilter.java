package com.epam.library.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private String requiredEncoding;

    @Override
    public void init(FilterConfig filterConfig) {
        requiredEncoding = filterConfig.getInitParameter("requestEncoding");
        if (requiredEncoding == null) requiredEncoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String currentEncoding = servletRequest.getCharacterEncoding();
        if (currentEncoding == null || !currentEncoding.equals(requiredEncoding)) {
            servletRequest.setCharacterEncoding(requiredEncoding);
            servletResponse.setCharacterEncoding(requiredEncoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
