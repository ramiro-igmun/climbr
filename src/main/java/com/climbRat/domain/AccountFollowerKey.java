package com.climbRat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data //TODO do we need this annotation? test it
@NoArgsConstructor
public class AccountFollowerKey implements Serializable {

  Long followerId;
  Long followingId;
}
