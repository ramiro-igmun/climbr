package com.climbook.climbook.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Account extends AbstractPersistable<Long> {

  private String userName;
  private String password;
  private String profileString;
  @OneToOne
  private Picture profilePicture;

  @OneToMany(mappedBy = "account")
  private List<Picture> pictures;

  @OneToMany(mappedBy = "author")
  private Set<WallPost> wallPosts;

  @OneToMany(mappedBy = "author")
  private Set<Comment> comments;

  @OneToMany(mappedBy = "follower")
  private Set<AccountFollower> followers;
  @OneToMany(mappedBy = "account")
  private Set<AccountFollower> following;



}
