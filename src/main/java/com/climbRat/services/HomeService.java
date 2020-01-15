package com.climbRat.services;

import com.climbRat.repositories.AccountRepository;
import com.climbRat.repositories.PictureRepository;
import com.climbRat.repositories.WallPostRepository;
import com.climbRat.domain.WallPost;
import com.climbRat.security.ClimbRatUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class HomeService {

  private AccountRepository accountRepository;
  private WallPostRepository wallPostRepository;
  private PictureRepository pictureRepository;

  @Autowired
  public HomeService(AccountRepository accountRepository, WallPostRepository wallPostRepository, PictureRepository pictureRepository) {
    this.accountRepository = accountRepository;
    this.wallPostRepository = wallPostRepository;
    this.pictureRepository = pictureRepository;
  }

  public ClimbRatUserDetails getUserDetails(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (ClimbRatUserDetails) auth.getPrincipal();
  }

  public List<WallPost> getHomePageWallPosts(){
    Pageable pageable = PageRequest.of(0,25, Sort.by("postDateTime").descending());
    return wallPostRepository.findCurrentUserHomePageWallPosts(getUserDetails().getCurrentUser(),pageable);
  }

  public byte[] getProfilePicture(){
     Optional<Long> optionalPictureId = pictureRepository.getDefaultPictureId(getUserDetails().getCurrentUser().getId());
     if (optionalPictureId.isPresent()){
       return pictureRepository.getOne(optionalPictureId.get()).getContent();
     }else{
       return pictureRepository.getByName("Default").getContent();
     }
  }

  public void saveWallPost(String message){
    WallPost wallPost = new WallPost();
    wallPost.setMessage(message);
    wallPost.setAuthor(accountRepository.findByUserName(getUserDetails().getUsername()).get());

    wallPostRepository.save(wallPost);
  }

  @Transactional
  public void addLikeToWallPost(Long wallPostId){
    Long currentUserId = getUserDetails().getCurrentUser().getId();
    if(!checkLikeExists(wallPostId,currentUserId)
            && wallPostRepository.getOne(wallPostId).getAuthor().getId().intValue() != currentUserId.intValue()){
    wallPostRepository.setWallPostLike(wallPostId, currentUserId);}
  }

  private boolean checkLikeExists(Long wallPostId, Long currentUserId) {
    return (wallPostRepository.checkIfLikeExists(wallPostId,currentUserId) == 1);
  }


}
