package com.climbr.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountFollowerKey implements Serializable {

  Long followerId;
  Long followingId;


}
