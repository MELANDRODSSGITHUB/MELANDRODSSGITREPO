package com.dss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVIE_ID")
    private long movieId;

    @NotBlank(message = "Image field is required")
    @Length(max = 50, message = "The field image must be less than 50 characters.")
    @Column(name = "IMAGE", nullable = false, length = 50)
    private String image;

    @NotBlank(message = "Title field is required")
    @Length(max = 50, message = "The field title must be less than 50 characters.")
    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Min(value = 1, message = "Enter valid cost amount")
    @Column(name = "COST")
    private int cost;

    @NotNull(message = "Movie release year is required")
    @Column(name = "YEAR_OF_RELEASE")
    private int yearOfRelease;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID")
    )
    private List<Actor> actors = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
}
