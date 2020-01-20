package com.climbRat.repositories;

import com.climbRat.domain.Account;
import com.climbRat.domain.FollowingFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowingFollowerRepository extends JpaRepository<FollowingFollower, Long> {

  List<FollowingFollower> findByFollowing(Account following);

  List<FollowingFollower> findByFollower(Account follower);
}
