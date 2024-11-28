package com.example.angular_spring_boot.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.angular_spring_boot.Exception.ResourceNotFoundException;
import com.example.angular_spring_boot.Model.Employee;
import com.example.angular_spring_boot.Model.UserPrincipal;
import com.example.angular_spring_boot.Repository.EmployeeRepository;

@Service
public class UserPrincipalService implements UserDetailsService {

  private EmployeeRepository employeeRepository;

  public UserPrincipalService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email_id) throws UsernameNotFoundException {
    Employee employee = employeeRepository.findByEmailID(email_id);

    if (employee == null) {
      throw new ResourceNotFoundException("User not found");
    }

    return new UserPrincipal(employee);
  }

}
