package services.interfaces;

import dto.ReviewDTO;
import validators.exceptions.reviews.ReviewIdIsNotValidException;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> findReviews();
    List<ReviewDTO> findReviews(Integer planeId);
    ReviewDTO findReview(Integer id);
    ReviewDTO addReview(ReviewDTO review);
    void deleteReview(Integer reviewId, String username);
    ReviewDTO updateReview(ReviewDTO review, String username);
}
