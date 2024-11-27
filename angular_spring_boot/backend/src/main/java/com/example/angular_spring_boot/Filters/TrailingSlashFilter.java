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

  /*
   * This is an alternative implementation starting at spring framework 6.2
   * 
   * @Override
   * protected void doFilterInternal(
   * HttpServletRequest request,
   * HttpServletResponse response,
   * FilterChain filterChain) throws ServletException, IOException {
   * // Redirecting
   * UrlHandlerFilter filter =
   * UrlHandlerFilter.trimTrailingSlash("/**").andRedirect(HttpStatus.
   * PERMANENT_REDIRECT)
   * .build();
   * 
   * // Or transparently handle those for HTTP clients, without any redirect:
   * UrlHandlerFilter filter =
   * UrlHandlerFilter.trimTrailingSlash("/**").andHandleRequest().build();
   * filter.doFilter(request, response, filterChain);
   * }
   */

  /*
   * @Override we can also extend this class with OncePerRequestFilter and return
   * a redirect
   * protected void doFilterInternal(
   * HttpServletRequest request,
   * HttpServletResponse response,
   * FilterChain filterChain) throws ServletException, IOException {
   * String requestUri = request.getRequestURI();
   * 
   * if (requestUri.endsWith("/")) {
   * String newUrl = requestUri.substring(0, requestUri.length() - 1);
   * response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
   * response.setHeader(HttpHeaders.LOCATION, newUrl);
   * return;
   * }
   * 
   * filterChain.doFilter(request, response);
   * }
   */

  /**
   * Alternitevly in the RestController we can always explicity
   * declare all routes:
   * @GetMapping({"/users/{uuid}", "/users/{uuid}/"})
   */
}
