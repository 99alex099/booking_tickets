package validators.exceptions.reviews;

import dto.ReviewDTO;
import lombok.Getter;

public class ReviewIdIsNotValidException extends ReviewException {

    public ReviewIdIsNotValidException(Integer reviewId) {
        super(ReviewDTO.builder()
                        .id(reviewId)
                        .build(),
                "review's ID " + reviewId + "isn't correct");
    }
}
