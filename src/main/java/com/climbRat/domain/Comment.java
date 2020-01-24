package com.climbRat.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Comment extends AbstractPersistable<Long> {

  @ManyToOne
  private Account author;
  private LocalDateTime postDateTime = LocalDateTime.now();
  @Column(length = 300)
  private String message;
  @OneToOne
  private Picture picture;

  @ManyToOne
  private WallPost parentPost;

  @ManyToMany(mappedBy = "likedComments")
  private List<Account> likes;
}
