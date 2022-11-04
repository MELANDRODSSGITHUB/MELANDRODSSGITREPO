package com.dss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateMovieRequest {

    private long movieId;

    @NotBlank(message = "Image field is required")
    @Length(max = 50, message = "The field image must be less than 50 characters.")
    private String image;

    @Min(value = 1, message = "Enter valid cost amount")
    private int cost;
}
