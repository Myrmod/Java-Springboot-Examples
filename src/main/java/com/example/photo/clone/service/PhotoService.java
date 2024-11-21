package com.example.photo.clone.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.photo.clone.model.Photo;
import com.example.photo.clone.repository.PhotoRepository;

//@Component
@Service
public class PhotoService {

  @Autowired
  private PhotoRepository photoRepository;

  public Iterable<Photo> findAll() {
    return photoRepository.findAll();
  }

	public Photo findOne(Integer id) {
		return photoRepository.findById(id).orElse(null);
	}

	public Photo insertOne(String filename, String contentType, byte[] bytes) {
    Photo photo = new Photo();

    photo.setFilename(filename);
    photo.setContentType(contentType);
    photo.setData(bytes);
		
    photoRepository.save(photo);

    return photo;
	}

	public void deleteOne(Integer id) {
		photoRepository.deleteById(id);
	}

}
