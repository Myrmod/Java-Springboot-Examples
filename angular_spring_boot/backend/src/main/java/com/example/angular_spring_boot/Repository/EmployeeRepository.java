package com.example.angular_spring_boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.angular_spring_boot.Model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
