package com.example.angular_spring_boot.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.angular_spring_boot.Exception.ResourceNotFoundException;
import com.example.angular_spring_boot.Model.Employee;
import com.example.angular_spring_boot.Repository.EmployeeRepository;
import com.example.angular_spring_boot.Services.UserPrincipalService;
import com.example.angular_spring_boot.Utility.EncryptionUtility;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

  private EmployeeRepository employeeRepository;
  private EncryptionUtility encryptionUtility;
  private UserPrincipalService userPrincipalService;

  @Value("${example.environment.encryptor.salt}")
  private String encryptorSalt;

  @Value("${example.environment.encryptor.password}")
  private String encryptorPassword;

  public EmployeeController(EmployeeRepository employeeRepository, EncryptionUtility encryptionUtility,
      UserPrincipalService userPrincipalService) {
    this.employeeRepository = employeeRepository;
    this.encryptionUtility = encryptionUtility;
    this.userPrincipalService = userPrincipalService;
  }

  @GetMapping("/test")
  public String getRoot() {
    return "Hello from root!";
  }

  // get all employees
  @GetMapping("/employees")
  public List<Employee> getAllEmployees() {
    return userPrincipalService.decryptEmployees(employeeRepository.findAll());
  }

  @PostMapping("/employees")
  public Employee creatEmployee(@RequestBody Employee employee) {

    // encryption
    employee.setPassword(encryptionUtility.encryptOneWay(employee.getPassword()));
    employee.setFirstName(encryptionUtility.encryptTwoWay(employee.getFirstName()));
    employee.setLastName(encryptionUtility.encryptTwoWay(employee.getLastName()));
    employee.setEmailID(encryptionUtility.encryptTwoWay(employee.getEmailID()));

    return userPrincipalService.decryptEmployee(employeeRepository.save(employee));
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exists with id " + id));

    // since throwing the custom exception would result in an error, we manually set
    // the response status to be ok
    return ResponseEntity.ok(userPrincipalService.decryptEmployee(employee));
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployeeById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exists with id " + id));

    employeeRepository.delete(employee);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employeeData) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exists with id " + id));

    if (employeeData.getFirstName() != null)
      employee.setFirstName(encryptionUtility.encryptTwoWay(employeeData.getFirstName()));
    if (employeeData.getLastName() != null)
      employee.setLastName(encryptionUtility.encryptTwoWay(employeeData.getLastName()));
    if (employeeData.getEmailID() != null)
      employee.setEmailID(encryptionUtility.encryptTwoWay(employeeData.getEmailID()));

    Employee updatedEmployee = employeeRepository.save(employee);

    return ResponseEntity.ok(userPrincipalService.decryptEmployee(updatedEmployee));
  }

}