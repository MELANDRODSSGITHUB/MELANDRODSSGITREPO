package com.dss.service;

import com.dss.entity.Actor;
import com.dss.entity.Movie;
import com.dss.exception.ActorHaveMovieDetailsException;
import com.dss.exception.ActorNotFoundException;
import com.dss.exception.DuplicateActorException;
import com.dss.exception.MovieNotFoundException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private MovieRepository movieRepository;;

    private ActorService actorService;

    private Actor actor;
    private Movie movie;

    @BeforeEach
    void setUp() {
        this.actorService = new ActorServiceImpl(this.actorRepository, this.movieRepository);
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
    }

    @Test
    void getAllActors() {
        actorService.getAllActors();
        verify(actorRepository).findAll();
    }

    @Test
    void addActorThrowDuplicateActorException(){
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Assertions.assertThrows(DuplicateActorException.class,() -> actorService.addActor(actor));
    }

    @Test
    void addActorSuccess(){
        Mockito.when(actorRepository.save(any(Actor.class))).thenReturn(actor);
        Assertions.assertEquals(actorService.addActor(actor),
                ResponseMsgConstant.SUCCESSFULLY_ADDED_ACTOR_MSG +
                " (Id: " + actor.getActorId() + "\t Name:" + actor.getFirstName()
                + " " + actor.getLastName() + ")");
    }

    @Test
    void deleteActorThrowActorNotFound() {
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ActorNotFoundException.class,() -> actorService.deleteActor(anyLong()));
    }

    @Test
    void deleteActorThrowActorHaveMovieDetailsException() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        actor.setMovies(movieList);
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Assertions.assertThrows(ActorHaveMovieDetailsException.class,() -> actorService.deleteActor(anyLong()));
    }

    @Test
    void deleteActorSuccess(){
        actor.setMovies(Collections.EMPTY_LIST);
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Assertions.assertEquals(actorService.deleteActor(anyLong()),
                ResponseMsgConstant.SUCCESSFULLY_DELETED_ACTOR_MSG.concat(
                        " (Id: " + actor.getActorId() + "\t Name:" + actor.getFirstName()
                                + " " + actor.getLastName() + ")"));
    }

    @Test
    void updateActorThrowActorNotFoundException(){
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ActorNotFoundException.class,() -> actorService.updateActor(actor));
    }

    @Test
    void updateActorSuccess(){
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Assertions.assertEquals(actorService.updateActor(actor),
                ResponseMsgConstant.SUCCESSFULLY_UPDATED_ACTOR_MSG +
                        " (Id: " + actor.getActorId() + "\t Name:" + actor.getFirstName()
                        + " " + actor.getLastName() + ")");
    }


    @Test
    void addMovieDetailsToTheActorThrowMovieNotFoundException(){
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(MovieNotFoundException.class,() -> actorService.addMovieDetailsToTheActor(actor.getActorId(),0));
    }

    @Test
    void addMovieDetailsToTheActorThrowActorNotFoundException(){
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ActorNotFoundException.class,() -> actorService.addMovieDetailsToTheActor(0,movie.getMovieId()));
    }

    @Test
    void addMovieDetailsToTheActorSuccess(){
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Mockito.when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actor));
        Assertions.assertEquals(actorService.addMovieDetailsToTheActor(actor.getActorId(),movie.getMovieId()),
                ResponseMsgConstant.SUCCESSFULLY_ADDED_MOVIE_DETAILS_TO_ACTOR_MSG);
    }
}
