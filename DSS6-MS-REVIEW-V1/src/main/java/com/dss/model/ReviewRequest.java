package com.dss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewRequest {

    @NotBlank(message = "Description field is required")
    @Length(max = 50, message = "The field description must be less than or equal to 45 characters.")
    private String description;

    @Min(value = 1, message = "Ratings should only be on 1 to 10.")
    @Max(value = 10, message = "Ratings should only be from 1 to 10.")
    private int rating;

    @NotNull(message = "Movie id field is required.")
    private long movieId;

    @NotNull(message = "Admin id field is required.")
    private int adminId;

}
