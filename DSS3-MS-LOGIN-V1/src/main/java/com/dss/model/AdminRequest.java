package com.dss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {

    private int adminId;

    @NotBlank(message = "First name field is required")
    @Length(max = 45, message = "The field first name must be less than or equal to 45 characters.")
    private String firstName;

    @NotBlank(message = "Last name field is required")
    @Length(max = 45, message = "The field last name must be less than or equal to 45 characters.")
    private String lastName;

    @NotBlank(message = "Password field is required")
    @Length(max = 45, message = "The field password must be less than or equal to 45 characters.")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase letter."),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+", message = "Password must contain at least one special character."),
            @Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")
    })
    private String password;

    @NotBlank(message = "Email address field is required")
    @Email(message = "Enter a valid email.")
    private String email;

    @Pattern(regexp = "^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{11}\\s*,?$", message = "Invalid mobile format")
    private String mobileNumber;
}
