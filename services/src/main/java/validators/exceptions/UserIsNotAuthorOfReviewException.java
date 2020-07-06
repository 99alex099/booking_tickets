package validators.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class UserIsNotAuthorOfReviewException extends RuntimeException {

    private String username;
    private Integer reviewId;

    public UserIsNotAuthorOfReviewException(String username, Integer reviewId) {
        super(username + " is not author of reviewId " + reviewId);
        this.username = username;
        this.reviewId = reviewId;
    }
}
