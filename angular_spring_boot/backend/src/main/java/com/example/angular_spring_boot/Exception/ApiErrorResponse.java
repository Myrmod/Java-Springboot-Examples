package com.example.angular_spring_boot.Exception;

public class ApiErrorResponse {
  private int code;
  private String message;

  public ApiErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }
}
