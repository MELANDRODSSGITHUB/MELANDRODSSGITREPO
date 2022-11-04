package com.dss.controller;


import com.dss.entity.Actor;
import com.dss.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dss/api/actor")
public class ActorController {

    private static final String GET_ALL_ACTOR_URL = "/getAll";
    private static final String ADD_ACTOR_URL = "/add";
    private static final String UPDATE_ACTOR_URL = "/update";
    private static final String UPDATE_MOVIE_ADD_ACTOR_URL = "/update/{actorId}/{movieId}";
    private static final String DELETE_ACTOR_URL = "/delete/{actorId}";
    @Autowired
    ActorService actorService;

    @PostMapping(ADD_ACTOR_URL)
    public ResponseEntity<Object> addActor(@Valid @RequestBody Actor actor) {
        return new ResponseEntity<>(actorService.addActor(actor), HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_ACTOR_URL)
    public ResponseEntity<List<Actor>> getAllActor() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(actorService.getAllActors());
    }

    @PutMapping(UPDATE_ACTOR_URL)
    public ResponseEntity<String> updateActor(@Valid @RequestBody Actor actor) {
        return new ResponseEntity<>(actorService.updateActor(actor), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_ACTOR_URL)
    public ResponseEntity<String> deleteActor(@PathVariable(value = "actorId") long actorId) {
        return new ResponseEntity<>(actorService.deleteActor(actorId), HttpStatus.OK);
    }

    @PutMapping(UPDATE_MOVIE_ADD_ACTOR_URL)
    public ResponseEntity<String> updateMovieAddActor(@PathVariable long actorId, @PathVariable long movieId) {
        return new ResponseEntity<>(actorService.addMovieDetailsToTheActor(actorId, movieId), HttpStatus.OK);
    }
}
