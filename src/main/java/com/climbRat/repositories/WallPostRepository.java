package com.climbRat.repositories;

import com.climbRat.domain.WallPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallPostRepository extends JpaRepository<WallPost, Long> {
}
