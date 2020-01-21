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

  private final WallPostRepository wallPostRepository;
  private final PictureRepository pictureRepository;

  public HomeService(WallPostRepository wallPostRepository,
                     PictureRepository pictureRepository) {
    this.wallPostRepository = wallPostRepository;
    this.pictureRepository = pictureRepository;
  }

  public List<WallPost> getHomePageWallPosts(Account currentUser){
    Pageable pageable = PageRequest.of(0,25, Sort.by("postDateTime").descending());
    return wallPostRepository.findCurrentUserHomePageWallPosts(currentUser,pageable);
  }

  public byte[] getPicture(Long id){
    Optional<Picture> picture = pictureRepository.findById(id);
    return picture.map(Picture::getContent).orElse(null);
  }

  public void saveWallPost(String message, Account currentUser){
    WallPost wallPost = new WallPost();
    wallPost.setMessage(message);
    wallPost.setAuthor(currentUser);

    wallPostRepository.save(wallPost);
  }

  @Transactional//TODO is this annotation necessary??
  public void addLikeToWallPost(Long wallPostId, Account currentUser){
    Long currentUserId = currentUser.getId();
    if(!checkLikeExists(wallPostId,currentUserId)
            && Objects.requireNonNull(wallPostRepository
            .getOne(wallPostId).getAuthor().getId()).intValue() != Objects.requireNonNull(currentUserId).intValue()){
    wallPostRepository.setWallPostLike(wallPostId, currentUserId);}
  }

  private boolean checkLikeExists(Long wallPostId, Long currentUserId) {
    return (wallPostRepository.checkIfLikeExists(wallPostId,currentUserId) == 1);
  }

}
