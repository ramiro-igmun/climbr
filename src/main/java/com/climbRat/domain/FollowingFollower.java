package com.climbRat.domain;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.time.LocalDateTime;

@Entity
@Data
public class FollowingFollower {

  @EmbeddedId
  AccountFollowerKey id;

  @ManyToOne
  @MapsId("followingId")
  private Account following;
  @ManyToOne
  @MapsId("followerId")
  private Account follower;

  private LocalDateTime followingStartingDate = LocalDateTime.now();
}
