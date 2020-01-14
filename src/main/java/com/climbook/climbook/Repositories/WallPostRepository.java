package com.climbook.climbook.Repositories;

import com.climbook.climbook.domain.WallPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallPostRepository extends JpaRepository<WallPost, Long> {
}
