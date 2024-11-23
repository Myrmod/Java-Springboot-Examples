package com.example.photo.clone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;

@Table("PHOTOS")
public class Photo {
  @Id
  private Integer id;

  @NotEmpty
  private String fileName;

  @JsonIgnore
  private byte[] data;

  private String contentType;

  public String getContentType() {
    return this.contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public byte[] getData() {
    return this.data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFilename() {
    return this.fileName;
  }

  public void setFilename(String filename) {
    this.fileName = filename;
  }

}
