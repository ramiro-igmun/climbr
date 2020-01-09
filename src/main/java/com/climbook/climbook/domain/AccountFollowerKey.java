package com.climbook.climbook.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class AccountFollowerKey implements Serializable {

  Long followerId;
  Long accountId;
}
