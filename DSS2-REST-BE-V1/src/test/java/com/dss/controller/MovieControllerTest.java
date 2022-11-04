package com.dss.controller;

import com.dss.entity.Actor;
import com.dss.entity.Movie;
import com.dss.model.SearchMovieRequest;
import com.dss.model.UpdateMovieRequest;
import com.dss.service.ActorService;
import com.dss.service.MovieService;
import com.dss.util.ResponseMsgConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest(classes = MovieControllerTest.class)

public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private Actor actor;
    private Movie movie;

    private UpdateMovieRequest updateMovieRequest;

    @BeforeEach
    void setUp() {
        actor = Actor.builder()
                .actorId(1)
                .firstName("TEST")
                .lastName("TEST")
                .age(32).gender("M")
                .movies(new ArrayList<>()).build();
        movie = Movie.builder()
                .movieId(1).cost(250).image("test.png")
                .title("MOVIE TITLE TEST")
                .yearOfRelease(2000)
                .actors(new ArrayList<>()).build();
        updateMovieRequest = UpdateMovieRequest.builder()
                .movieId(1).cost(200).image("updatedTest,png")
                .build();
    }

    @Test
    void getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        Mockito.when(movieService.getAllMovie()).thenReturn(movieList);
        ResponseEntity<List<Movie>> response = movieController.getAllMovies();
        Assertions.assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    void addMovie() {
        Mockito.when(movieService.addMovie(any(Movie.class))).thenReturn(anyString());
        ResponseEntity<String> response = movieController.addMovie(movie);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateMovie() {
        Mockito.when(movieService.updateMovie(any(UpdateMovieRequest.class))).thenReturn(anyString());
        ResponseEntity<String> response = movieController.updateMovie(updateMovieRequest);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteMovie() {
        Mockito.when(movieService.deleteMovie(anyLong())).thenReturn(anyString());
        ResponseEntity<String> response = movieController.deleteMovie(movie.getMovieId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateMovieAddActor() {
        Mockito.when(movieService.addActorDetailsToTheMovie(anyLong(), anyLong())).thenReturn(ResponseMsgConstant.SUCCESSFULLY_ADDED_ACTOR_DETAILS_TO_MOVIE_MSG);
        Assertions.assertEquals(movieController.updateMovieAddActor(actor.getActorId(), actor.getActorId()).getStatusCode(), HttpStatus.OK);
    }

    @Test
    void searchMovie() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        Mockito.when(movieService.searchMovie(any(SearchMovieRequest.class))).thenReturn(movieList);
        Assertions.assertEquals(HttpStatus.FOUND, movieController.searchMovie(any(SearchMovieRequest.class)).getStatusCode());
    }
}
