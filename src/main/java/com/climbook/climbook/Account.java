package com.climbook.climbook;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Account extends AbstractPersistable<Long> {

  private String userName;
  private String password;
  private String profileString;

  @OneToOne
  private Picture profilePicture;
  @OneToMany(mappedBy = "account")
  private List<Picture> pictures;

  @ManyToMany(mappedBy = "following")
  private Set<Account> followers;
  @ManyToMany
  private Set<Account> following;



}
