package com.climbRat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class WallPost extends AbstractPersistable<Long> {

  @ManyToOne
  private Account author;
  private LocalDateTime postDateTime = LocalDateTime.now();
  @Column(length = 1000)
  private String message;

  @OneToMany(mappedBy = "parentPost")
  private Set<Comment> comments;

  @ManyToMany(mappedBy = "likedWallPosts")
  Set<Account> likes;
}
