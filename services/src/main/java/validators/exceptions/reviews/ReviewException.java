package validators.exceptions.reviews;

import dto.ReviewDTO;
import lombok.Getter;

public class ReviewException extends RuntimeException {
    @Getter
    private ReviewDTO review;

    public ReviewException(ReviewDTO review, String message) {
        super(message);
        this.review = review;
    }
}
