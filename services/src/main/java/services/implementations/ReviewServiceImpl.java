package services.implementations;

import dao.jpa.PlaneRepository;
import dao.jpa.ReviewRepository;
import dao.jpa.UserRepository;
import dto.ReviewDTO;
import dto.converters.interfaces.PlaneConverter;
import dto.converters.interfaces.ReviewConverter;
import dto.converters.interfaces.UserConverter;
import entity.Review;
import org.apache.log4j.Logger;
import services.interfaces.ReviewService;
import org.springframework.stereotype.Service;
import validators.exceptions.reviews.ReviewIdIsNotValidException;
import validators.exceptions.UserIsNotAuthorOfReviewException;
import validators.interfaces.Validator;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;
    private final Validator<ReviewDTO> reviewValidator;

    private static final Logger LOGGER = Logger.getLogger(ReviewServiceImpl.class);

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewConverter reviewConverter,
                             Validator<ReviewDTO> reviewValidator) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
        this.reviewValidator = reviewValidator;
    }

    @Override
    public List<ReviewDTO> findReviews() {
        LOGGER.info("trying to find all reviews");
        return reviewConverter
                .convertEntityToDTO(
                        reviewRepository.findAll()
                );
    }

    @Override
    public List<ReviewDTO> findReviews(Integer planeId) {
        LOGGER.info("getting review of plane[id:" + planeId + "]");
        return  reviewConverter
                .convertEntityToDTO(
                        reviewRepository.findAllByReviewedPlaneId(planeId)
                );
    }

    @Override
    public ReviewDTO findReview(Integer id) {
        LOGGER.info("finding review[id:" + id + "]");
        return reviewConverter
                .convertEntityToDTO(
                        reviewRepository.findById(id).orElseThrow(
                                () -> new ReviewIdIsNotValidException(id)
                        )
                );
    }

    @Override
    public ReviewDTO addReview(ReviewDTO review) {
        review.setId(null); // review валидный и невалидный
        reviewValidator.isValid(review);

        LOGGER.info("trying to add review " + review.getText());

        return reviewConverter.convertEntityToDTO(
                reviewRepository.save(
                        reviewConverter.convertDTOToEntity(review)
                )
        );
    }

    @Override
    public void deleteReview(Integer reviewId, String username) throws ReviewIdIsNotValidException {
        if (reviewRepository.existsById(reviewId)) {
            LOGGER.info("trying to delete review[id:" + reviewId + "]");
            Review review = reviewRepository.findByIdAndAuthorName(reviewId, username).orElseThrow(
                    () -> new UserIsNotAuthorOfReviewException(username, reviewId));
            reviewRepository.delete(review);
        } else {
            throw new ReviewIdIsNotValidException(reviewId);
        }
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO review, String username) {
        reviewValidator.isValid(review);
        if (reviewRepository.existsById(review.getId())) {
            LOGGER.info(username + " trying to set review[id:" + review.getId() + "]");
            Review originalReview = reviewRepository.findByIdAndAuthorName(review.getId(), username).orElseThrow(
                    () -> new UserIsNotAuthorOfReviewException(username, review.getId()));
            if (originalReview.getAuthor().getUsername().equals(
                    review.getAuthor().getUsername())
                    && review.getAuthor().getUsername().equals(
                    username
            )) {

                Review updatedReview = reviewRepository.save(
                        reviewConverter.convertDTOToEntity(review));

                return reviewConverter.convertEntityToDTO(updatedReview);
            } else {
                throw new UserIsNotAuthorOfReviewException(username, review.getId());
            }
        } else {
            throw new ReviewIdIsNotValidException(review.getId());
        }
    }
}
