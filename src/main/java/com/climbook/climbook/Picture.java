package com.climbook.climbook;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Data
public class Picture extends AbstractPersistable<Long> {

  private String name;
  private String mediaType;
  private Long size;

  @ManyToOne
  private Account account;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] content;
}
