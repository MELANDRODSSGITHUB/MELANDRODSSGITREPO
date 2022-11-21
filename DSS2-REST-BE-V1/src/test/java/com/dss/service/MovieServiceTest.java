package com.dss.service;

import com.dss.entity.Actor;
import com.dss.entity.Movie;
import com.dss.exception.ActorNotFoundException;
import com.dss.exception.DeleteNewMovieException;
import com.dss.exception.DuplicateMovieException;
import com.dss.exception.MovieNotFoundException;
import com.dss.model.AddMovieRequest;
import com.dss.model.SearchMovieRequest;
import com.dss.model.UpdateMovieRequest;
import com.dss.repository.ActorRepository;
import com.dss.repository.MovieRepository;
import com.dss.util.ResponseMsgConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    private MovieService movieService;
    private Actor actor;
    private Movie movie;

    private AddMovieRequest addMovieRequest;

    private UpdateMovieRequest updateMovieRequest;
    private SearchMovieRequest searchMovieRequest;

    @BeforeEach
    void setUp() {
        this.movieService = new MovieServiceImpl(this.movieRepository, this.actorRepository);
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
        addMovieRequest = AddMovieRequest.builder()
                .movieId(1).cost(250).image("test.png")
                .title("MOVIE TITLE TEST")
                .yearOfRelease(2000).build();
        updateMovieRequest = UpdateMovieRequest.builder()
                .movieId(1).cost(200).image("updatedTest,png")
                .build();
        searchMovieRequest = SearchMovieRequest.builder()
                .movieId(1)
                .title("TEST").build();
    }

    @Test
    void getAllMovies() {
        movieService.getAllMovie();
        verify(movieRepository).findAll();
    }

    @Test
    void addMovieThrowDuplicateMovieException() {
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Assertions.assertThrows(DuplicateMovieException.class, () -> movieService.addMovie(addMovieRequest));
    }

    @Test
    void addMovieSuccess() {
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        Assertions.assertEquals(movieService.addMovie(addMovieRequest),
                ResponseMsgConstant.SUCCESSFULLY_ADDED_MOVIE_MSG.concat(" (id: "
                        + movie.getMovieId() + "\t Title:" + movie.getTitle() + ")"));
    }

    @Test
    void deleteMovieThrowMovieNotFoundException() {
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(anyLong()));
    }

    @Test
    void deleteMovieThrowDeleteNewMovieException() {
        movie.setYearOfRelease(2022);
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Assertions.assertThrows(DeleteNewMovieException.class, () -> movieService.deleteMovie(movie.getMovieId()));
    }

    @Test
    void deleteMovieSuccess() {
        movie.setYearOfRelease(2020);
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Assertions.assertEquals(movieService.deleteMovie(anyLong()),
                ResponseMsgConstant.SUCCESSFULLY_DELETED_MOVIE_MSG.concat(" (Id: " + movie.getMovieId()
                        + "\t Title:" + movie.getTitle() + ")"));
    }

    @Test
    void updateMovieThrowMovieNotFoundException() {
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(updateMovieRequest));
    }

    @Test
    void updateMovieSuccess() {
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Assertions.assertEquals(movieService.updateMovie(updateMovieRequest),
                ResponseMsgConstant.SUCCESSFULLY_UPDATED_MOVIE_MSG.concat(" (Id: "
                        + movie.getMovieId() + "Tile: " + movie.getTitle() + ")"));
    }


    @Test
    void addActorDetailsToTheMovieThrowActorNotFoundException() {
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ActorNotFoundException.class, () -> movieService.addActorDetailsToTheMovie(0, movie.getMovieId()));
    }

    @Test
    void addActorDetailsToTheMovieThrowMovieNotFoundException() {
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.addActorDetailsToTheMovie(actor.getActorId(), 0));
    }

    @Test
    void addActorDetailsToTheMovieSuccess() {
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Assertions.assertEquals(ResponseMsgConstant.SUCCESSFULLY_ADDED_ACTOR_DETAILS_TO_MOVIE_MSG,
                movieService.addActorDetailsToTheMovie(actor.getActorId(), movie.getMovieId()));
    }

    @Test
    void searchMovie() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        Mockito.when(movieRepository.findMovieByMovieIdOrTitle(anyLong(),anyString())).thenReturn(movieList);
        Assertions.assertNotNull( movieService.searchMovie(searchMovieRequest));
        verify(movieRepository).findMovieByMovieIdOrTitle(anyLong(),anyString());
    }

    @Test
    void searchMovieThrowMovieNotFoundException() {
        Mockito.when(movieRepository.findMovieByMovieIdOrTitle(anyLong(),anyString())).thenReturn(null);
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.searchMovie(searchMovieRequest));
        verify(movieRepository).findMovieByMovieIdOrTitle(anyLong(),anyString());
    }

    @Test
    void searchMovieThrowMovieNotFoundExceptionEmpty() {
        List<Movie> movieList = new ArrayList<>();
        Mockito.when(movieRepository.findMovieByMovieIdOrTitle(anyLong(),anyString())).thenReturn(movieList);
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.searchMovie(searchMovieRequest));
        verify(movieRepository).findMovieByMovieIdOrTitle(anyLong(),anyString());
    }
}
