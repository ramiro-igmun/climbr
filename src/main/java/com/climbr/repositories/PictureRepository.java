package com.climbr.repositories;

import com.climbr.domain.Picture;
import com.climbr.domain.WallPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

Optional<Picture> findByParentPost(WallPost parentPost);
}
