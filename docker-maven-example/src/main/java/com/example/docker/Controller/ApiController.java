package com.example.docker.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ApiController {

  @GetMapping("/")
  public String getHello() {
    return "hello from docker!";
  }
}
