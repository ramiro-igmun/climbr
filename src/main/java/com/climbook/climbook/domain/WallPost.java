package com.climbook.climbook.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.time.Instant;
import java.util.Set;

@Entity
@Data
public class WallPost extends AbstractPersistable<Long> {

  @ManyToOne
  private Account author;
  private Date postDate = new Date(Instant.now().toEpochMilli());
  @Column(length = 1000)
  private String message;
  @OneToMany(mappedBy = "parentPost")
  private Set<Comment> comments;

}
