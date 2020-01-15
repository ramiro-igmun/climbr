package com.climbRat.repositories;

import com.climbRat.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PictureRepository extends JpaRepository<Picture, Long> {
  @Query(value = "SELECT p.ID FROM PICTURE p JOIN ACCOUNT A on p.ID = A.PROFILE_PICTURE_ID WHERE A.ID = ?1",
          nativeQuery = true)
  Optional<Long> getDefaultPictureId(Long accountId);
  Picture getByName(String name);
}
