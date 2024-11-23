package com.example.photo.clone.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.photo.clone.exceptions.NoPhotoFoundException;
import com.example.photo.clone.model.ApiError;

@ControllerAdvice
public class RestExceptionController {
  
  @ExceptionHandler(value = NoPhotoFoundException.class)
  public ResponseEntity<ApiError> handleNoPhotoFoundException() {

    ApiError error = new ApiError(400, "No Photo found", new Date());

    return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
  }

}
