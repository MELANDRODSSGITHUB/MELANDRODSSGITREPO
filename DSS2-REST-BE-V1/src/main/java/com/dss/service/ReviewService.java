package com.dss.service;

import com.dss.entity.Review;
import com.dss.model.ReviewRequest;

import java.util.List;

public interface ReviewService {

    String getAllReviews();

    String addReview(ReviewRequest reviewRequest);

}
