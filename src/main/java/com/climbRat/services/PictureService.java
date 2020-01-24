package com.climbRat.services;

import com.climbRat.domain.Picture;
import com.climbRat.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PictureService {

  private final PictureRepository pictureRepository;

  public PictureService(PictureRepository pictureRepository) {
    this.pictureRepository = pictureRepository;
  }

  public byte[] getPicture(Long id){
    Optional<Picture> picture = pictureRepository.findById(id);
    return picture.map(Picture::getContent).orElse(null);
  }
}
