package com.climbr.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FollowingFollowerKey implements Serializable {

  Long followerId;
  Long followingId;

}
