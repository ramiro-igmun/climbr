package com.climbr.repositories;

import com.climbr.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PictureRepository extends JpaRepository<Picture, Long> {

  @Query("SELECT p FROM Picture p JOIN Account a ON p.id = a.profilePicture.id WHERE a.id = ?1")
  Optional<Picture> getDefaultPicture(Long accountId);

}
