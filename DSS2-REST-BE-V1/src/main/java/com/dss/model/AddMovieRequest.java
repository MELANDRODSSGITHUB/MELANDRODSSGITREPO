package com.dss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieRequest {

    private long movieId;

    @NotBlank(message = "Image field is required")
    @Length(max = 50, message = "The field image must be less than 50 characters.")
    private String image;

    @NotBlank(message = "Title field is required")
    @Length(max = 50, message = "The field title must be less than 50 characters.")
    private String title;

    @Min(value = 1, message = "Enter valid cost amount")
    private int cost;

    @NotNull(message = "Movie release year is required")
    private int yearOfRelease;
}

