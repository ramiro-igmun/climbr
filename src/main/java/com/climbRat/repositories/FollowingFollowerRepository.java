package com.climbRat.repositories;

import com.climbRat.domain.Account;
import com.climbRat.domain.FollowingFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowingFollowerRepository extends JpaRepository<FollowingFollower, Long> {

  @Query("SELECT f.following FROM FollowingFollower f WHERE f.follower = ?1")
  List<Account> getFollowing(Account follower);

  @Query("SELECT f.follower FROM FollowingFollower f WHERE f.following = ?1")
  List<Account> getFollowers(Account following);
}
