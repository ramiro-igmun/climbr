package com.climbRat.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Picture extends AbstractPersistable<Long> {

  private String name;
  private String mediaType;
  private String description;
  private Long size;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] content;

  @ManyToMany(mappedBy = "likedPictures")
  private List<Account> likes;
}
