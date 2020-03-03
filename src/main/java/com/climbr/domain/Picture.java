package com.climbr.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Picture extends AbstractPersistable<Long> {

  private String name;
  private String mediaType;
  private Long size;

  @OneToOne(fetch = FetchType.LAZY)
  private WallPost parentPost;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] content;

  @Override
  public String toString() {
    return "Picture{" +
            "id=" + getId() +
            ", name='" + name + '\'' +
            ", mediaType='" + mediaType + '\'' +
            ", size=" + size +
            '}';
  }
}
