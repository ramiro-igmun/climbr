package com.climbRat.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Comment extends AbstractPersistable<Long> {

  @ManyToOne
  private Account author;
  @ManyToOne
  private WallPost parentPost;
  private Date date = new Date(Instant.now().toEpochMilli());

  private String commentContent;

  @ManyToMany(mappedBy = "likedComments")
  private List<Account> likes;
}
