package com.climbRat.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class WallPost extends AbstractPersistable<Long> {

  @ManyToOne
  private Account author;
  private LocalDateTime postDateTime = LocalDateTime.now();
  @Column(length = 300)
  private String message;
  @OneToOne
  private Picture picture;

  @OneToMany(mappedBy = "parentPost")
  private List<Comment> comments;

  @ManyToMany
  List<Account> likes;
}
