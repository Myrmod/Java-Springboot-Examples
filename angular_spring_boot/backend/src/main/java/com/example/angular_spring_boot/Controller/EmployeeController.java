package com.example.angular_spring_boot.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

  private EmployeeRepository employeeRepository;

  public EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  // get all employees
  @GetMapping("/employees")
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @PostMapping("/employees")
  public Employee creatEmployee(@RequestBody Employee employee) {
    return employeeRepository.save(employee);
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exists with id " + id));

    // since throwing the custom exception would result in an error, we manually set
    // the response status to be ok
    return ResponseEntity.ok(employee);
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
      employee.setFirstName(employeeData.getFirstName());
    if (employeeData.getLastName() != null)
      employee.setLastName(employeeData.getLastName());
    if (employeeData.getEmailID() != null)
      employee.setEmailID(employeeData.getEmailID());

    Employee updatedEmployee = employeeRepository.save(employee);

    return ResponseEntity.ok(updatedEmployee);
  }

}