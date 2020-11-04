package com.yosa.theopengalaxy.repositories;

import com.yosa.theopengalaxy.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByProjectId(Long id, Pageable pageable);
    Optional<Review> findByProjectIdAndAuthorId(Long projectId, Long authorId);
    Optional<Review> findByProjectIdAndAuthorUsername(Long projectId, String username);
    Optional<Review> findByIdAndAuthorUsername(Long id, String username);
}
