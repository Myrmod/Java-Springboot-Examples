package com.example.angular_spring_boot.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}