package com.climbr.repositories;

import com.climbr.domain.Account;
import com.climbr.domain.FollowingFollower;
import com.climbr.domain.FollowingFollowerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowingFollowerRepository extends JpaRepository<FollowingFollower, FollowingFollowerKey> {

  @Query("SELECT f.following FROM FollowingFollower f WHERE f.follower = ?1")
  List<Account> getFollowing(Account follower);

  @Query("SELECT f.follower FROM FollowingFollower f WHERE f.following = ?1")
  List<Account> getFollowers(Account following);

  @Query("SELECT (COUNT(f) > 0) FROM FollowingFollower f WHERE f.follower=?2 AND f.following=?1")
  boolean isFollowerOfUser(Account followedUser, Account follower);

  @Modifying
  @Query("DELETE FROM FollowingFollower f WHERE f.following.id = ?1 and f.follower.id = ?2")
  void deleteFollowerFollowing(Long accountId, Long currentUserId);

}
