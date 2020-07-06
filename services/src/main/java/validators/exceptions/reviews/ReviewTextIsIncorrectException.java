package validators.exceptions.reviews;

import dto.ReviewDTO;
import validators.exceptions.general.InvalidTextException;

public class ReviewTextIsIncorrectException extends ReviewException {

    public ReviewTextIsIncorrectException(ReviewDTO review) {
        super(review,
                "Review's text '" + review.getText() + "' is incorrect:" +
                        "text must to be contains only letters, numbers, space(' ') and '-'");
    }
}
