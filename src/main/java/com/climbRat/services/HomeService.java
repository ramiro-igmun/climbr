package com.climbRat.services;

import com.climbRat.domain.FollowingFollower;
import com.climbRat.domain.WallPost;
import com.climbRat.repositories.AccountRepository;
import com.climbRat.repositories.FollowingFollowerRepository;
import com.climbRat.repositories.PictureRepository;
import com.climbRat.repositories.WallPostRepository;
import com.climbRat.security.ClimbRatUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HomeService {

  private final AccountRepository accountRepository;
  private final WallPostRepository wallPostRepository;
  private final PictureRepository pictureRepository;
  private final FollowingFollowerRepository followingFollowerRepository;

  public HomeService(AccountRepository accountRepository, WallPostRepository wallPostRepository,
                     PictureRepository pictureRepository, FollowingFollowerRepository followingFollowerRepository) {
    this.accountRepository = accountRepository;
    this.wallPostRepository = wallPostRepository;
    this.pictureRepository = pictureRepository;
    this.followingFollowerRepository = followingFollowerRepository;
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
            && Objects.requireNonNull(wallPostRepository.getOne(wallPostId).getAuthor().getId()).intValue() != Objects.requireNonNull(currentUserId).intValue()){
    wallPostRepository.setWallPostLike(wallPostId, currentUserId);}
  }

  private boolean checkLikeExists(Long wallPostId, Long currentUserId) {
    return (wallPostRepository.checkIfLikeExists(wallPostId,currentUserId) == 1);
  }


  public List<FollowingFollower> getFollowers() {
    return followingFollowerRepository.findByFollowing(getUserDetails().getCurrentUser());
  }

  public List<FollowingFollower> getFollowedAccounts(){
    return followingFollowerRepository.findByFollower(getUserDetails().getCurrentUser());
  }
}
