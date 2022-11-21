package com.dss.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMovieRequest {

    private long movieId;
    @NotBlank(message = "Title field is required")
    @Length(max = 50, message = "The field title must be less than 50 characters.")
    private String title;
}
