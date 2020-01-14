package com.climbRat.repositories;

import com.climbRat.domain.Account;
import com.climbRat.domain.WallPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WallPostRepository extends JpaRepository<WallPost, Long> {
  List<WallPost> findByAuthor(Account author);
}
