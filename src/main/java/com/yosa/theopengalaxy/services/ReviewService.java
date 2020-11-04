package com.yosa.theopengalaxy.services;

import com.yosa.theopengalaxy.domain.Project;
import com.yosa.theopengalaxy.domain.Review;
import com.yosa.theopengalaxy.exceptions.BadRequestException;
import com.yosa.theopengalaxy.exceptions.NotFoundException;
import com.yosa.theopengalaxy.repositories.ProjectRepository;
import com.yosa.theopengalaxy.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProjectService projectService;

    public Page<Review> getReviews(Long project_id, int page){
        return reviewRepository.findByProjectId(project_id,
                PageRequest.of(page, 30, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Optional<Review> getReviewByUsernameAndProject(Long project_id, String username){
        return reviewRepository.findByProjectIdAndAuthorUsername(project_id, username);
    }

    public Long deleteReview(Long id, String username){
        Review review = reviewRepository.findByIdAndAuthorUsername(id, username)
                .orElseThrow(() -> new NotFoundException("Review not found"));
        Long project_id = review.getProject().getId();
        Project project = projectService.getById(project_id);
        project.setRate(project.getRate()-review.getRateType().getDirection());
        reviewRepository.delete(review);
        return project_id;
    }

    public Review createReview(Review review){
        Optional<Review> reviewOptional = reviewRepository.findByProjectIdAndAuthorId(review.getProject().getId(), review.getAuthor().getId());
        if(reviewOptional.isPresent())
            throw new BadRequestException("You are already have a review");
        Project project = projectService.getById(review.getProject().getId());
        project.setRate(project.getRate()+review.getRateType().getDirection());
        return reviewRepository.save(review);
    }
}
