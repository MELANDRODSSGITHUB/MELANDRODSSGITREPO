package com.dss.service;

import com.dss.entity.Admin;
import com.dss.entity.Movie;
import com.dss.entity.Review;
import com.dss.exception.AdminNotFoundException;
import com.dss.exception.DuplicateReviewException;
import com.dss.exception.MovieNotFoundException;
import com.dss.model.ReviewRequest;
import com.dss.repository.AdminRepository;
import com.dss.repository.MovieRepository;
import com.dss.repository.ReviewRepository;
import com.dss.util.ResponseMsgConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ReviewRepository reviewRepository;

    private ReviewService reviewService;

    private Movie movie;
    private Admin admin;
    private Review review;
    private ReviewRequest reviewRequest;

    @BeforeEach
    void setUp() {
        this.reviewService = new ReviewServiceImpl(this.reviewRepository, this.movieRepository, this.adminRepository);
        movie = Movie.builder()
                .movieId(1).cost(250).image("test.png")
                .title("MOVIE TITLE TEST")
                .yearOfRelease(2000)
                .actors(new ArrayList<>()).build();

        admin = Admin.builder()
                .adminId(1)
                .firstName("TEST_FIRSTNAME")
                .lastName("TEST_LASTNAME")
                .email("test@gmail.com")
                .password("P@ssw0rd")
                .mobileNumber("09179224585")
                .reviewList(new ArrayList<>()).build();

        review = Review.builder()
                .reviewId(1)
                .description("GREAT_TEST")
                .rating(10)
                .postedDate(LocalDateTime.now()).build();
        reviewRequest = ReviewRequest.builder()
                .adminId(1)
                .movieId(1)
                .description("GREAT_TEST")
                .rating(10).build();
    }

    @Test
    void getAllReview() {
        reviewService.getAllReviews();
        verify(reviewRepository).findAll();
    }

    @Test
    void getAllReviewWithResult() {
        List<Review> reviewList = new ArrayList<>();
        Review review2 = Review.builder()
                .reviewId(2).movie(movie).description("Test 2")
                        .rating(10).admin(admin).build();
        reviewList.add(review);
        reviewList.add(review2);
        Mockito.when(reviewRepository.findAll()).thenReturn(reviewList);
        reviewService.getAllReviews();
        verify(reviewRepository).findAll();
    }

    @Test
    void addReviewThrowDuplicateReviewException() {
        Mockito.when(reviewRepository.findById(anyInt())).thenReturn(Optional.ofNullable(review));
        Assertions.assertThrows(DuplicateReviewException.class, () -> reviewService.addReview(reviewRequest));

        verify(reviewRepository).findById(anyInt());
        verify(adminRepository, times(0)).findById(anyInt());
        verify(movieRepository, times(0)).findById(anyLong());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    void addReviewThrowAdminNotFoundException() {
        Mockito.when(reviewRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Mockito.when(adminRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(AdminNotFoundException.class, () -> reviewService.addReview(reviewRequest));

        verify(reviewRepository).findById(anyInt());
        verify(adminRepository).findById(anyInt());
        verify(movieRepository, times(0)).findById(anyLong());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    void addReviewThrowMovieNotFoundException() {
        Mockito.when(reviewRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Mockito.when(adminRepository.findById(anyInt())).thenReturn(Optional.ofNullable(admin));
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(MovieNotFoundException.class, () -> reviewService.addReview(reviewRequest));

        verify(reviewRepository).findById(anyInt());
        verify(adminRepository).findById(anyInt());
        verify(movieRepository).findById(anyLong());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    void addReviewSuccess() {
        Mockito.when(reviewRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Mockito.when(adminRepository.findById(anyInt())).thenReturn(Optional.ofNullable(admin));
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(review);
        Assertions.assertEquals(reviewService.addReview(reviewRequest),
                ResponseMsgConstant.SUCCESSFULLY_ADDED__REVIEW_TO_MOVIE_MSG);

        verify(reviewRepository).findById(anyInt());
        verify(adminRepository).findById(anyInt());
        verify(movieRepository).findById(anyLong());
        verify(reviewRepository).save(any(Review.class));

    }
}
