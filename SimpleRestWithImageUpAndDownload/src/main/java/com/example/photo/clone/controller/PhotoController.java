package com.example.photo.clone.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.photo.clone.exceptions.NoPhotoFoundException;
import com.example.photo.clone.model.Photo;
import com.example.photo.clone.service.PhotoService;

@RestController
public class PhotoController {

  @Autowired
  private PhotoService photoService;

  @GetMapping("/")
  public String hello() {
    return "Hello World";
  }

  @GetMapping("/photos")
  public Iterable<Photo> get() {
    return photoService.findAll();
  }

  /**
   * If this route is being called like `curl /throw-error/error`, 
   * then an error will be thrown.
   * This error goes to the RestExceptionController. Which uses the
   * annotation ControllerAdvice. It is globally available and catches
   * errors defined in methods using the 
   * ExceptionHandler(value = MyCustomException.class) annotation.
   * There we use a custom ApiError object, to create a structured error
   * response.
   */
  @GetMapping("/throw-error/{fail}")
  public String throwError(@PathVariable String fail) {
    
    if (fail.length() > 0) {
      throw new NoPhotoFoundException("No photo found");
    }

    return "You should add another segment to the path to see the error handling.";
  }

  @GetMapping("/photos/{id}")
  public Photo get(@PathVariable Integer id) {
    Photo photo = photoService.findOne(id);

    if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    return photo;
  }

  @DeleteMapping("/photos/{id}")
  public void delete(@PathVariable Integer id) {
    photoService.deleteOne(id);
  }

  @PostMapping("/photos")
  public Photo create(@RequestPart("data") MultipartFile file) throws IOException {

    Photo photo = photoService.insertOne(file.getOriginalFilename(), file.getContentType(), file.getBytes());

    return photo;
  }
}
