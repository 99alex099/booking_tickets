package handlers;

import dto.ErrorDTO;
import dto.ReviewDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.reviews.MarkIsIncorrectException;
import validators.exceptions.reviews.ReviewIdIsNotValidException;
import validators.exceptions.reviews.ReviewTextIsIncorrectException;

@ControllerAdvice
public class ReviewHandler {
    @ExceptionHandler(MarkIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<ReviewDTO> markIsIncorrect(final MarkIsIncorrectException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getReview());
    }
    @ExceptionHandler(ReviewIdIsNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorDTO<ReviewDTO> idIsIncorrect(final ReviewIdIsNotValidException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.NOT_FOUND,
                e.getReview());
    }
    @ExceptionHandler(ReviewTextIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<ReviewDTO> textIsIncorrect(final ReviewTextIsIncorrectException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getReview());
    }
}
