package com.example.angular_spring_boot.Model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
  private Employee employee;

  public UserPrincipal(Employee employee) {
    this.employee = employee;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("USER"));
  }

  @Override
  public String getPassword() {
    return employee.getPassword();
  }

  @Override
  public String getUsername() {
    return employee.getEmailID();
  }

}
