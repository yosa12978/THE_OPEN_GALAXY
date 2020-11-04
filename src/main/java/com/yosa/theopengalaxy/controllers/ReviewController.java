package com.yosa.theopengalaxy.controllers;

import com.yosa.theopengalaxy.domain.Review;
import com.yosa.theopengalaxy.dtos.ReviewReadDto;
import com.yosa.theopengalaxy.services.AccountService;
import com.yosa.theopengalaxy.services.ProjectService;
import com.yosa.theopengalaxy.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AccountService accountService;

    private static Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("create/{id}")
    private String createReview(@PathVariable("id") Long id, ReviewReadDto reviewDto, Principal principal){
        Review review = new Review();
        review.setRateType(reviewDto.getRateType());
        review.setProject(projectService.getById(id));
        review.setAuthor(accountService.getByUsername(principal.getName()));
        review.setPubDate(new Date());
        if(reviewDto.getText() != null)
            review.setText(reviewDto.getText());
        Review createdReview = reviewService.createReview(review);
        logger.info("Created review with id = " + createdReview.getId());
        return "redirect:/projects/detail/"+id;
    }

    @GetMapping("delete/{id}")
    private String deleteReview(@PathVariable("id") Long id, Principal principal){
        Long deletedReview = reviewService.deleteReview(id, principal.getName());
        logger.info("Deleted review with id = " + id);
        return "redirect:/projects/detail/"+deletedReview;
    }
}
