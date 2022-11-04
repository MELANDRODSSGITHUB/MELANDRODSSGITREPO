package com.dss.controller;

import com.dss.model.ReviewRequest;
import com.dss.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dss2/api/review")
public class ReviewController {

    private static final String GET_ALL_REVIEW_URL = "/getAll";
    private static final String ADD_REVIEW_URL = "/register";

    @Autowired
    private ReviewService reviewService;

    @PostMapping(ADD_REVIEW_URL)
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return new ResponseEntity<>(reviewService.addReview(reviewRequest), HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_REVIEW_URL)
    public ResponseEntity<String> getAllReviews() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getAllReviews());
    }

}
