package com.example.photo.clone.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.photo.clone.model.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
  
}
