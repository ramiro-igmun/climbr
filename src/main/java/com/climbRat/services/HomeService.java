package com.climbRat.services;

import com.climbRat.domain.Account;
import com.climbRat.domain.FollowingFollower;
import com.climbRat.domain.Picture;
import com.climbRat.repositories.AccountRepository;
import com.climbRat.repositories.FollowingFollowerRepository;
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
  private FollowingFollowerRepository followingFollowerRepository;

  @Autowired
  public HomeService(AccountRepository accountRepository, WallPostRepository wallPostRepository,
                     PictureRepository pictureRepository, FollowingFollowerRepository followingFollowerRepository) {
    this.accountRepository = accountRepository;
    this.wallPostRepository = wallPostRepository;
    this.pictureRepository = pictureRepository;
    this.followingFollowerRepository = followingFollowerRepository;
  }

  public Account getCurrentUserAccount(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ClimbRatUserDetails currentUserDetails = (ClimbRatUserDetails) auth.getPrincipal();
    return currentUserDetails.getCurrentUser();
  }

  public List<WallPost> getHomePageWallPosts(){
    Pageable pageable = PageRequest.of(0,25, Sort.by("postDateTime").descending());
    return wallPostRepository.findCurrentUserHomePageWallPosts(getCurrentUserAccount(),pageable);
  }

  public byte[] getPicture(Long id){
    Optional<Picture> picture = pictureRepository.findById(id);
    return picture.map(Picture::getContent).orElse(pictureRepository.getByName("Default").getContent());
  }

  public void saveWallPost(String message){
    WallPost wallPost = new WallPost();
    wallPost.setMessage(message);
    wallPost.setAuthor(accountRepository.getOne(getCurrentUserAccount().getId()));

    wallPostRepository.save(wallPost);
  }

  @Transactional
  public void addLikeToWallPost(Long wallPostId){
    Long currentUserId = getCurrentUserAccount().getId();
    if(!checkLikeExists(wallPostId,currentUserId)
            && wallPostRepository.getOne(wallPostId).getAuthor().getId().intValue() != currentUserId.intValue()){
    wallPostRepository.setWallPostLike(wallPostId, currentUserId);}
    System.out.println(wallPostRepository.getOne(wallPostId).getLikes().size());
  }

  private boolean checkLikeExists(Long wallPostId, Long currentUserId) {
    return (wallPostRepository.checkIfLikeExists(wallPostId,currentUserId) == 1);
  }


  public List<FollowingFollower> getFollowers() {
    return followingFollowerRepository.findByFollowing(getCurrentUserAccount());
  }

  public List<FollowingFollower> getFollowedAccounts(){
    return followingFollowerRepository.findByFollower(getCurrentUserAccount());
  }
}
