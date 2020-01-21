package com.climbRat.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data //TODO do we need this annotation? test it
public class AccountFollowerKey implements Serializable {

  Long followerId;
  Long accountId;
}
