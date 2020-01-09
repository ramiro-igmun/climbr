package com.climbook.climbook.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.Instant;

@Entity
@Data
public class Comment extends AbstractPersistable<Long> {

  @ManyToOne
  Account author;
  @ManyToOne
  WallPost parentPost;
  Date date = new Date(Instant.now().toEpochMilli());

  String commentContent;
}
