package com.climbRat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Picture extends AbstractPersistable<Long> {

  private String name;
  private String mediaType;
  private String description;
  private Long size;

  @ManyToOne
  private Account account;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] content;

  @ManyToMany(mappedBy = "likedPictures")
  private List<Account> likes;
}
