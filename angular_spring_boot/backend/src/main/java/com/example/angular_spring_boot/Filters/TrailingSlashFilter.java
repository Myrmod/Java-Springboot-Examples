package com.example.angular_spring_boot.Filters;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * https://github.com/spring-projects/spring-framework/issues/28552
 * This maps requests with trailling slash to those without
 */
@Component
public class TrailingSlashFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequestWrapper requestWrappper = new HttpServletRequestWrapper((HttpServletRequest) request) {
      @Override
      public String getRequestURI() {
        String requestURI = super.getRequestURI();

        return StringUtils.trimTrailingCharacter(requestURI, "/".toCharArray()[0]);
      }
    };

    chain.doFilter(requestWrappper, response);
  }

}
