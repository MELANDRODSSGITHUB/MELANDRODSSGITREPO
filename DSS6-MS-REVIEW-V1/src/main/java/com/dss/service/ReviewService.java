package com.dss.service;

import com.dss.model.ReviewRequest;

public interface ReviewService {

    String getAllReviews();

    String addReview(ReviewRequest reviewRequest);

}
