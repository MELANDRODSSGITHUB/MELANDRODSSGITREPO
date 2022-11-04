package com.dss.controller;

import com.dss.model.ReviewRequest;
import com.dss.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Formatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(classes = MovieControllerTest.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private ReviewController reviewController;
    private ReviewRequest reviewRequest;

    @BeforeEach
    public void setUp() {
        reviewRequest = ReviewRequest.builder()
                .adminId(1)
                .movieId(1)
                .description("GREAT_TEST")
                .rating(10).build();
    }

    @Test
    public void getAllReviews() {
        Formatter fmt = new Formatter();
        fmt.format("%10s %30s %10s %20s %20s\n", "Review Id", "Description", "Rating", "Movie Title", "Admin Name");
        fmt.format("%100s", "No Data");
        Mockito.when(reviewService.getAllReviews()).thenReturn(fmt.toString());
        ResponseEntity<String> response = reviewController.getAllReviews();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addReview() {
        Mockito.when(reviewService.addReview(any(ReviewRequest.class))).thenReturn(anyString());
        ResponseEntity<String> response = reviewController.addReview(reviewRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
