package com.climbRat.repositories;

import com.climbRat.domain.Account;
import com.climbRat.domain.WallPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WallPostRepository extends JpaRepository<WallPost, Long> {
  @Query("SELECT w FROM WallPost w WHERE w.author = ?1 OR w.author IN " +
          "(SELECT f.following FROM FollowingFollower f WHERE f.follower = ?1)")
  List<WallPost> findCurrentUserHomePageWallPosts(Account currentUser, Pageable pageable);

}
