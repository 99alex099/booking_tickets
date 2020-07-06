package controllers;

import dto.ReviewDTO;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDTO> findReviews() {
        return reviewService.findReviews();
    }
    @GetMapping("{id}")
    public ReviewDTO findReview(@PathVariable Integer id) {
        return reviewService.findReview(id);
    }
    @DeleteMapping("{id}")
    public void deleteReview(@PathVariable Integer id) {
        String name = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        reviewService.deleteReview(id, name);
    }
    @PutMapping("{id}")
    public ReviewDTO updateReview(@PathVariable Integer id,
                             @RequestBody ReviewDTO review) {
        review.setId(id);
        String name = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return reviewService.updateReview(review, name);
    }
}