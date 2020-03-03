package com.climbr.services;

import com.climbr.domain.Account;
import com.climbr.domain.Picture;
import com.climbr.domain.WallPost;
import com.climbr.repositories.AccountRepository;
import com.climbr.repositories.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PictureService {

  private final PictureRepository pictureRepository;
  private AccountRepository accountRepository;
  private AccountService accountService;

  public PictureService(PictureRepository pictureRepository, AccountRepository accountRepository, AccountService accountService) {
    this.pictureRepository = pictureRepository;
    this.accountRepository = accountRepository;
    this.accountService = accountService;
  }

  public byte[] getPicture(Long id){
    Optional<Picture> picture = pictureRepository.findById(id);

    return picture.map(Picture::getContent).orElse(null);
  }

  @Transactional
  public void makeProfilePicture(Long pictureID){
    Picture picture;
    if (pictureID == 0){
      picture = null;
    }else {
      picture = pictureRepository.getOne(pictureID);
    }
    Account currentUser = accountRepository.getOne(accountService.getCurrentUserAccountIfAuthenticated().getId());
    Picture oldProfilePicture = currentUser.getProfilePicture();
    if (oldProfilePicture != null) {
      if (oldProfilePicture.getParentPost() == null) {
        pictureRepository.delete(oldProfilePicture);
      }
    }
    currentUser.setProfilePicture(picture);
    accountService.getCurrentUserAccountIfAuthenticated().setProfilePicture(picture);
    accountService.saveUser(currentUser);
  }

  public Picture savePicture(MultipartFile image, WallPost post) throws IOException {
    Picture picture = new Picture();
    picture.setContent(image.getBytes());
    picture.setMediaType(image.getContentType());
    picture.setName(image.getOriginalFilename());
    picture.setSize(image.getSize());
    picture.setParentPost(post);
    pictureRepository.save(picture);
    return picture;
  }

}
