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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    AdminRepository adminRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository, AdminRepository adminRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Get all reviews.
     *
     * @return List of all reviews.
     */
    @Override
    public String getAllReviews() {
        Formatter fmt = new Formatter();
        String response = "";
        try {
            List<Review> reviewList = reviewRepository.findAll();

            fmt.format("%10s %30s %10s %20s %20s\n", "Review Id", "Description", "Rating", "Movie Title", "Admin Name");
            for (Review review : reviewList) {
                fmt.format("%10s %30s %10s %20s %20s\n", review.getReviewId(), review.getDescription(),
                        review.getRating(),
                        (review.getMovie() == null ? " " : review.getMovie().getTitle()),
                        (review.getAdmin() == null ? " " : review.getAdmin().getFirstName() + " " + review.getAdmin().getLastName()));
            }
            if (reviewList.isEmpty()) {
                fmt.format("%100s", "No Data");
            }
            response = fmt.toString();

        } finally {
            fmt.close();
        }
        return response;
    }

    /**
     * Add review to the movie.
     *
     * @param reviewRequest - Contains review request data that will be added to movie.
     * @return - Message response.
     */
    @Override
    public String addReview(ReviewRequest reviewRequest) {
        Review review = Review.builder().description(reviewRequest.getDescription())
                .rating(reviewRequest.getRating())
                .postedDate(LocalDateTime.now()).build();
        Optional<Review> reviewOptional = reviewRepository.findById(review.getReviewId());
        if (reviewOptional.isPresent()) {
            throw new DuplicateReviewException(ResponseMsgConstant.FAILED_TO_ADD_REVIEW_TO_MOVIE_MSG);
        }
        Optional<Admin> adminOptional = adminRepository.findById(reviewRequest.getAdminId());
        if (!adminOptional.isPresent()) {
            throw new AdminNotFoundException(ResponseMsgConstant.FAILED_TO_ADD_REVIEW_TO_MOVIE_MSG);
        }

        Optional<Movie> movieOptional = movieRepository.findById(reviewRequest.getMovieId());
        if (!movieOptional.isPresent()) {
            throw new MovieNotFoundException(ResponseMsgConstant.FAILED_TO_ADD_REVIEW_TO_MOVIE_MSG);
        }

        review.setAdmin(adminOptional.get());
        review.setMovie(movieOptional.get());
        reviewRepository.save(review);

        return ResponseMsgConstant.SUCCESSFULLY_ADDED__REVIEW_TO_MOVIE_MSG;
    }
}
