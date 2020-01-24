package com.climbRat.services;

import com.climbRat.domain.Account;
import com.climbRat.domain.WallPost;
import com.climbRat.repositories.PictureRepository;
import com.climbRat.repositories.WallPostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class WallPostService {

  private final WallPostRepository wallPostRepository;

  public WallPostService(WallPostRepository wallPostRepository) {
    this.wallPostRepository = wallPostRepository;
  }

  public List<WallPost> getHomePageWallPosts(Account currentUser){
    Pageable pageable = PageRequest.of(0,25, Sort.by("postDateTime").descending());
    return wallPostRepository.findCurrentUserHomePageWallPosts(currentUser,pageable);
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
