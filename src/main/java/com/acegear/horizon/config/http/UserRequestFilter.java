package com.acegear.horizon.config.http;

import com.acegear.horizon.domain.repository.jpa.UserTokenRepository;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UserRequestFilter implements Filter {
    private UserTokenRepository userTokenRepository;

    public UserRequestFilter(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserRequestWrapper userRequestWrapper = new UserRequestWrapper((HttpServletRequest) servletRequest, userTokenRepository);
        filterChain.doFilter(userRequestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
