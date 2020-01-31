package com.climbr.domain;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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
