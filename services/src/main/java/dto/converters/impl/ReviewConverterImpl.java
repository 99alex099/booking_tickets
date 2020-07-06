package dto.converters.impl;

import dto.ReviewDTO;
import dto.converters.interfaces.ReviewConverter;
import entity.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewConverterImpl extends ConverterImpl<Review, ReviewDTO>
        implements ReviewConverter {
}
