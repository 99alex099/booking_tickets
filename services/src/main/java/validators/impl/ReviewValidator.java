package validators.impl;

import dto.ReviewDTO;
import org.apache.log4j.Logger;
import validators.exceptions.reviews.MarkIsIncorrectException;
import validators.exceptions.reviews.ReviewTextIsIncorrectException;
import org.springframework.stereotype.Service;
import validators.interfaces.Validator;

@Service
public class ReviewValidator extends ValidatorImpl implements Validator<ReviewDTO> {

    private static final Logger LOGGER = Logger.getLogger(ReviewValidator.class);
    @Override
    public void isValid(ReviewDTO reviewDTO) {
        LOGGER.info("validator checks mark of review");
        if (reviewDTO.getMark() == null || !markIsValid(reviewDTO.getMark())) {
            throw new MarkIsIncorrectException(reviewDTO);
        }
        LOGGER.info("validator checks text of review");
        if (textIsNotValid(reviewDTO.getText())) {
            throw new ReviewTextIsIncorrectException(reviewDTO);
        }
    }

    private boolean markIsValid(Integer mark) {
        return mark >= 0 && mark <= 10;
    }

    private boolean textIsNotValid(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (symbolIsNotLetter(text.charAt(i))
                    && symbolIsNotNumber(text.charAt(i))
                    && text.charAt(i) != ' '
                    && text.charAt(i) != '-') {
                        return true;
                    }
        }
        return false;
    }
}