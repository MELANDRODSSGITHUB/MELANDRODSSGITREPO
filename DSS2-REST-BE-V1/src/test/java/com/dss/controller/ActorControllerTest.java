package com.dss.controller;

import com.dss.entity.Actor;
import com.dss.model.ActorRequest;
import com.dss.service.ActorService;
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

@SpringBootTest(classes = ActorControllerTest.class)
public class ActorControllerTest {

    @Mock
    private ActorService actorService;

    @InjectMocks
    private ActorController actorController;

    private Actor actor;

    private ActorRequest actorRequest;

    @BeforeEach
    public void setUp() {
        actor = Actor.builder()
                .actorId(1)
                .firstName("TEST")
                .lastName("TEST")
                .age(32).gender("M")
                .movies(new ArrayList<>()).build();
        actorRequest = ActorRequest.builder()
                .actorId(1)
                .firstName("TEST")
                .lastName("TEST")
                .age(32).gender("M").build();
    }

    @Test
    public void getAllActors() {
        List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);
        Mockito.when(actorService.getAllActors()).thenReturn(actorList);
        ResponseEntity<List<Actor>> response = actorController.getAllActor();
        Assertions.assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    public void addActor() {
        Mockito.when(actorService.addActor(any(ActorRequest.class))).thenReturn(anyString());
        ResponseEntity<Object> response = actorController.addActor(actorRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void updateActor() {
        Mockito.when(actorService.updateActor(any(ActorRequest.class))).thenReturn(anyString());
        ResponseEntity<String> response = actorController.updateActor(actorRequest);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteActor() {
        Mockito.when(actorService.deleteActor(anyLong())).thenReturn(anyString());
        ResponseEntity<String> response = actorController.deleteActor(actor.getActorId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateActorAddMovie() {
        Mockito.when(actorService.addMovieDetailsToTheActor(anyLong(), anyLong())).thenReturn(ResponseMsgConstant.SUCCESSFULLY_ADDED_MOVIE_DETAILS_TO_ACTOR_MSG);
        Assertions.assertEquals(actorController.updateMovieAddActor(actor.getActorId(),actor.getActorId()).getStatusCode(), HttpStatus.OK);
    }
}
