package com.climbRat.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Data
public class Picture extends AbstractPersistable<Long> {

  private String name;
  private String mediaType;
  private Long size;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;
  @OneToOne(fetch = FetchType.LAZY)
  private WallPost parentPost;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] content;

}
