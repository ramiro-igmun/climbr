package com.climbRat.repositories;

import com.climbRat.domain.Account;
import com.climbRat.domain.WallPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WallPostRepository extends JpaRepository<WallPost, Long> {

  @Query("SELECT w FROM WallPost w WHERE w.author = ?1 OR w.author IN " +
          "(SELECT f.following FROM FollowingFollower f WHERE f.follower = ?1)")
  List<WallPost> findCurrentUserHomePageWallPosts(Account currentUser, Pageable pageable);

  @Modifying(flushAutomatically = true)
  @Query(value = "INSERT INTO WALL_POST_LIKES (LIKED_WALL_POSTS_ID, LIKES_ID) VALUES (?1,?2)", nativeQuery = true)
  void setWallPostLike(Long wallPostId, Long accountId);

  @Query(value = "SELECT COUNT(1) FROM WALL_POST_LIKES WHERE LIKED_WALL_POSTS_ID = ?1 AND LIKES_ID = ?2" ,nativeQuery = true)
  int checkIfLikeExists(Long wallPostId, Long accountId);
  }


