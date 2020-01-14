package com.climbook.climbook.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
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
  private Set<FollowingFollower> followers;
  @OneToMany(mappedBy = "following")
  private Set<FollowingFollower> following;

  @ManyToMany
  private Set<Comment> likedComments;
  @ManyToMany
  private Set<Picture> likedPictures;
  @ManyToMany
  private Set<WallPost> likedWallPosts;

}
