package com.climbRat.services;

import com.climbRat.domain.Account;
import com.climbRat.domain.FollowingFollower;
import com.climbRat.domain.Picture;
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

import java.util.stream.Collectors;
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
            && Objects.requireNonNull(wallPostRepository.getOne(wallPostId).getAuthor().getId()).intValue() != Objects.requireNonNull(currentUserId).intValue()){
    wallPostRepository.setWallPostLike(wallPostId, currentUserId);}
  }

  private boolean checkLikeExists(Long wallPostId, Long currentUserId) {
    return (wallPostRepository.checkIfLikeExists(wallPostId,currentUserId) == 1);
  }


  public List<Account> getFollowers() {
    return followingFollowerRepository.findByFollowing(getCurrentUserAccount()).stream()
            .map(FollowingFollower::getFollower).collect(Collectors.toList());
  }

  public List<Account> getFollowing(){
    return followingFollowerRepository.findByFollower(getCurrentUserAccount()).stream()
            .map(FollowingFollower::getFollowing).collect(Collectors.toList());
  }
}
