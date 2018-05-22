package com.acegear.horizon.config;

import com.acegear.horizon.config.http.UserRequestFilter;
import com.acegear.horizon.domain.repository.jpa.UserTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private UserTokenRepository userTokenRepository;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.addUrlPatterns("/*");
        UserRequestFilter userRequestFilter = new UserRequestFilter(userTokenRepository);
        filterRegistrationBean.setFilter(userRequestFilter);
        return filterRegistrationBean;
    }
}
