package services.implementations;

import dao.jpa.ReviewRepository;
import dto.ReviewDTO;
import dto.UserDTO;
import dto.converters.interfaces.Converter;
import dto.converters.interfaces.ReviewConverter;
import entity.Review;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import validators.exceptions.UserIsNotAuthorOfReviewException;
import validators.exceptions.flights.ArrivalCityNameIsIncorrectExceptionException;
import validators.exceptions.reviews.ReviewException;
import validators.exceptions.reviews.ReviewIdIsNotValidException;
import validators.exceptions.reviews.ReviewTextIsIncorrectException;
import validators.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ReviewServiceImplTest {


    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewConverter reviewConverter;
    @Mock
    Validator<ReviewDTO> reviewValidator;
    @InjectMocks
    ReviewServiceImpl reviewService;

    private Review reviewEntity =
            Review.builder()
                    .id(4)
                    .text("aga")
                    .mark(3)
                    .author(
                            User.builder()
                            .username("user")
                            .build()
                    )
                    .build();

    private ReviewDTO reviewDTO =
            ReviewDTO.builder()
                    .id(4)
                    .text("aga")
                    .mark(3)
                    .author(
                            UserDTO.builder()
                                    .username("user")
                                    .build()
                    )
                    .build();

    private List<ReviewDTO> reviewDTOs = new ArrayList<>();
    private List<Review> reviewEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findReviews() {
        when(reviewRepository.findAll()).thenReturn(reviewEntities);
        when(reviewConverter.convertEntityToDTO(reviewEntities)).thenReturn(reviewDTOs);

        assertEquals(reviewDTOs, reviewService.findReviews());
    }

    @Test
    void findReviewsByID() {
        when(reviewRepository.findAllByReviewedPlaneId(4)).thenReturn(reviewEntities);
        when(reviewConverter.convertEntityToDTO(reviewEntities)).thenReturn(reviewDTOs);

        assertEquals(reviewDTOs, reviewService.findReviews(4));
    }

    @Test
    void findReviewIsSuccessfully() {


        Optional<Review> reviewOptional = Optional.of(reviewEntity);

        when(reviewRepository.findById(4)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);

        assertEquals(reviewDTO, reviewService.findReview(4));
    }

    @Test
    void findReviewIdIsIncorrect() {


        Optional<Review> reviewOptional = Optional.empty();

        when(reviewRepository.findById(4)).thenReturn(reviewOptional);
        try {
            reviewService.findReview(4);
            fail("Expected ReviewIdIsNotValidException");
        } catch (ReviewIdIsNotValidException e) {
            assertTrue(true);
        }
    }

    @Test
    void addReviewIsSuccessfully() {
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
        assertEquals(reviewDTO, reviewService.addReview(reviewDTO));
    }
    @Test
    void addReviewWhenReviewIsNotValid() {

        doThrow(ReviewException.class)
                .when(reviewValidator)
                .isValid(reviewDTO);

        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        try {
            reviewService.addReview(reviewDTO);
            fail("Expected ReviewException");
        } catch (ReviewException e) {
            assertTrue(true);
        }
    }

    @Test
    void deleteReviewIsSuccessfully() {
        String username = "user";
        Optional<Review> reviewOptional = Optional.of(reviewEntity);
        when(reviewRepository.existsById(4)).thenReturn(true);
        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        reviewService.deleteReview(4, username);
        verify(reviewRepository, times(1)).delete(reviewEntity);
    }
    @Test
    void deleteReviewIdIsIncorrect() {
        String username = "user";
        Optional<Review> reviewOptional = Optional.empty();
        when(reviewRepository.existsById(4)).thenReturn(false);
        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);

        try {
            reviewService.deleteReview(4, username);
            fail("Expected ReviewIdIsNotValidException");
        } catch (ReviewIdIsNotValidException e) {
            assertTrue(true);
        }
    }
    @Test
    void deleteReviewUserIsNotAuthor() {
        String username = "user";
        Optional<Review> reviewOptional = Optional.empty();
        when(reviewRepository.existsById(4)).thenReturn(true);
        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);

        try {
            reviewService.deleteReview(4, username);
            fail("Expected UserIsNotAuthorOfReviewException");
        } catch (UserIsNotAuthorOfReviewException e) {
            assertTrue(true);
        }
    }
    @Test
    void updateReviewIsSuccessfully() {
        String username = "user";
        when(reviewRepository.existsById(4)).thenReturn(true);
        Optional<Review> reviewOptional = Optional.of(reviewEntity);

        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        assertEquals(reviewDTO, reviewService.updateReview(reviewDTO, username));
    }
    @Test
    void updateReviewIsNotValid() {
        String username = "user";

        doThrow(ReviewTextIsIncorrectException.class)
                .when(reviewValidator)
                .isValid(reviewDTO);

        when(reviewRepository.existsById(4)).thenReturn(true);
        Optional<Review> reviewOptional = Optional.of(reviewEntity);

        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        try {
            reviewService.updateReview(reviewDTO, username);
            fail("Expected ReviewException");
        } catch (ReviewTextIsIncorrectException e) {
            assertTrue(true);
        }
    }
    @Test
    void updateReviewIdIsIncorrect() {
        String username = "user";


        when(reviewRepository.existsById(4)).thenReturn(false);
        Optional<Review> reviewOptional = Optional.of(reviewEntity);

        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        try {
            reviewService.updateReview(reviewDTO, username);
            fail("Expected ReviewIdIsNotException");
        } catch (ReviewIdIsNotValidException e) {
            assertTrue(true);
        }
    }

    @Test
    void updateReviewIdIsNotAuthorFirstCondition() {
        String username = "other_user";

        Review reviewEntityDB =
                Review.builder()
                        .id(4)
                        .text("aga")
                        .mark(3)
                        .author(
                                User.builder()
                                        .username("other_user")
                                        .build()
                        )
                        .build();

        when(reviewRepository.existsById(4)).thenReturn(true);
        Optional<Review> reviewOptional = Optional.of(reviewEntityDB);

        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        try {
            reviewService.updateReview(reviewDTO, username);
            fail("Expected ReviewIdIsNotException");
        } catch (UserIsNotAuthorOfReviewException e) {
            assertTrue(true);
        }
    }

    @Test
    void updateReviewIdIsNotAuthorSecondCondition() {
        String username = "user";

        ReviewDTO reviewDTODB =
                ReviewDTO.builder()
                        .id(4)
                        .text("aga")
                        .mark(3)
                        .author(
                                UserDTO.builder()
                                        .username("other_user")
                                        .build()
                        )
                        .build();

        when(reviewRepository.existsById(4)).thenReturn(true);
        Optional<Review> reviewOptional = Optional.of(reviewEntity);

        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        try {
            reviewService.updateReview(reviewDTODB, username);
            fail("Expected ReviewIdIsNotException");
        } catch (UserIsNotAuthorOfReviewException e) {
            assertTrue(true);
        }
    }
    @Test
    void updateReviewIdIsNotAuthorInDB() {
        String username = "user";

        when(reviewRepository.existsById(4)).thenReturn(true);
        Optional<Review> reviewOptional = Optional.empty();

        when(reviewRepository.findByIdAndAuthorName(4, username)).thenReturn(reviewOptional);
        when(reviewConverter.convertEntityToDTO(reviewEntity)).thenReturn(reviewDTO);
        when(reviewConverter.convertDTOToEntity(reviewDTO)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);

        try {
            reviewService.updateReview(reviewDTO, username);
            fail("Expected ReviewIdIsNotException");
        } catch (UserIsNotAuthorOfReviewException e) {
            assertTrue(true);
        }
    }
}