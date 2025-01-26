package de.myrmod.security.Filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * https://github.com/spring-projects/spring-framework/issues/28552
 * This maps requests without trailing slash to those with
 */
@Profile("basic")
@Component
public class TrailingSlashFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequestWrapper requestWrappper = new HttpServletRequestWrapper((HttpServletRequest) request) {
      @Override
      public String getRequestURI() {
        String requestURI = super.getRequestURI();

        if (requestURI.contains(".")) {
          return requestURI;
        }

        if (!requestURI.endsWith("/")) {
          requestURI += "/";
        }

        return requestURI;
      }
    };

    chain.doFilter(requestWrappper, response);
  }

}
