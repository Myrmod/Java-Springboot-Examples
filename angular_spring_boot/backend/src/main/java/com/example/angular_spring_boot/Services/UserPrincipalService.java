package com.example.angular_spring_boot.Services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.angular_spring_boot.Exception.ResourceNotFoundException;
import com.example.angular_spring_boot.Model.Employee;
import com.example.angular_spring_boot.Model.UserPrincipal;
import com.example.angular_spring_boot.Repository.EmployeeRepository;
import com.example.angular_spring_boot.Utility.EncryptionUtility;

@Service
public class UserPrincipalService implements UserDetailsService {

  private EmployeeRepository employeeRepository;
  private EncryptionUtility encryptionUtility;

  public UserPrincipalService(EmployeeRepository employeeRepository, EncryptionUtility encryptionUtility) {
    this.employeeRepository = employeeRepository;
    this.encryptionUtility = encryptionUtility;
  }

  @Override
  public UserDetails loadUserByUsername(String email_id) throws UsernameNotFoundException {
    Employee employee = findUserByEmailAndDecrypt(email_id);
    if (employee == null) {
      throw new ResourceNotFoundException("User not found");
    }

    return new UserPrincipal(employee);
  }

  private Employee findUserByEmailAndDecrypt(String email_id) {
    List<Employee> employees = employeeRepository.findAll();
    for (Employee employee : employees) {
      if (encryptionUtility.decryptTwoWay(employee.getEmailID()).equals(email_id)) {
        employee.setFirstName(encryptionUtility.decryptTwoWay(employee.getFirstName()));
        employee.setLastName(encryptionUtility.decryptTwoWay(employee.getLastName()));
        employee.setEmailID(email_id);

        return employee;
      }
    }

    return null;
  }

  public Employee decryptEmployee(Employee employee) {
    employee.setFirstName(encryptionUtility.decryptTwoWay(employee.getFirstName()));
    employee.setLastName(encryptionUtility.decryptTwoWay(employee.getLastName()));
    employee.setEmailID(encryptionUtility.decryptTwoWay(employee.getEmailID()));

    return employee;
  }

  public List<Employee> decryptEmployees(List<Employee> employees) {
    return employees.stream().map(employee -> {
      return decryptEmployee(employee);
    }).toList();
  }
}
