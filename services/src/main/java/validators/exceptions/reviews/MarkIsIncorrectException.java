package validators.exceptions.reviews;

import dto.ReviewDTO;
import validators.exceptions.general.InvalidTextException;

public class MarkIsIncorrectException extends ReviewException {

    public MarkIsIncorrectException(ReviewDTO review) {
        super(review,
                "Review's mark " + review.getMark() + " is incorrect:" +
                        "mark must to be above 0 and below 10");
    }
}
