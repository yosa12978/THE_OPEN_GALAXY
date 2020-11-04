package com.yosa.theopengalaxy.repositories;

import com.yosa.theopengalaxy.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByAuthorUsername(String username, Pageable pageable);
    Optional<Project> findByIdAndAuthorUsername(Long id, String username);
    @Query("select p from Project p where p.title like %:query%")
    Page<Project> search(@Param("query") String query, Pageable pageable);
    Page<Project> findByRateGreaterThanEqual(Long rate, Pageable pageable);
    @Query("select p from Project p where :tag member of p.tags")
    Page<Project> findByTags(@Param("tag") String tag, Pageable pageable);
}
