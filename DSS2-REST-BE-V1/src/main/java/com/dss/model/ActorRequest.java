package com.dss.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorRequest {

    private long actorId;

    @NotBlank(message = "First name field is required")
    @Length(max = 25, message = "The field first name must be less than 25 characters.")
    private String firstName;

    @NotBlank(message = "Last name field is required")
    @Length(max = 25, message = "The field last name must be less than 25 characters.")
    private String lastName;

    @Pattern(regexp = "[MFX]", message = "Invalid entry for gender. \n M = Male \n F = Female \n X = Unknown")
    private String gender;

    @Range(min = 1, max = 150, message = "Enter a valid age.")
    private int age;

}
