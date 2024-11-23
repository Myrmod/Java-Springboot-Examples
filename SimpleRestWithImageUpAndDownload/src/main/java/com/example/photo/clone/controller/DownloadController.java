package com.example.photo.clone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.photo.clone.model.Photo;
import com.example.photo.clone.service.PhotoService;

@RestController
public class DownloadController {

  @Autowired
  private PhotoService photoService;

  @GetMapping("/download/{id}")
  public ResponseEntity<byte[]> download(@PathVariable Integer id) {

    Photo photo = photoService.findOne(id);

    if (photo == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    byte[] data = photo.getData();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf(photo.getContentType()));
    ContentDisposition build = ContentDisposition.builder("inline").filename(photo.getFilename()).build();
    headers.setContentDisposition(build);

    return new ResponseEntity<>(data, headers, HttpStatus.OK);
  }

}
