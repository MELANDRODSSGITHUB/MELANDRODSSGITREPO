package com.dss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACTORS")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTOR_ID")
    private long actorId;

    @NotBlank(message = "First name field is required")
    @Length(max = 25, message = "The field first name must be less than 25 characters.")
    @Column(name = "FIRST_NAME", nullable = false, length = 25)
    private String firstName;

    @NotBlank(message = "Last name field is required")
    @Length(max = 25, message = "The field last name must be less than 25 characters.")
    @Column(name = "LAST_NAME", nullable = false, length = 25)
    private String lastName;


    @Pattern(regexp = "M|F|X", message = "Invalid entry for gender. \n M = Male \n F = Female \n X = Unknown")
    @Column(name = "GENDER")
    private String gender;

    @Range(min = 1, max = 150, message = "Enter a valid age.")
    @Column(name = "AGE")
    private int age;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL)
    private List<Movie> movies = new ArrayList<>();
}
